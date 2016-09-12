package exercise1;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: GetSysDescrForever <ip-address> <snmp-port>");
            System.exit(-1);
        }

        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        Address targetAddress = new UdpAddress(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
        CommunityTarget target = new CommunityTarget(targetAddress, new OctetString("public"));
        target.setVersion(SnmpConstants.version1);

        RegisteryHelperUtil.getInstance().registerMetrics(initMetrics());

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring_context.xml");
        GetSysDescrForever getSysDescrForever = ctx.getBean("getSysDescrForever", GetSysDescrForever.class);
        while (true) {
            try {
                getSysDescrForever.run(snmp, target);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static MetricRegistry initMetrics() {
        MetricRegistry registry = new MetricRegistry();
        final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(5, TimeUnit.SECONDS);
        return registry;
    }
}