package exercise1;

import org.apache.log4j.BasicConfigurator;
import org.snmp4j.agent.io.ImportModes;
import org.snmp4j.agent.test.SnapshotAgent;
import org.snmp4j.smi.OctetString;

import java.io.IOException;

public class Simulator extends SnapshotAgent {

    private int engineBoots;


    public Simulator() throws IOException {
        super(null, null, null);
    }

    @Override
    protected int getEngineBoots() {
        return engineBoots;
    }

    public void setEngineBoots(int engineBoots) {
        this.engineBoots = engineBoots;
    }

    @Override
    public void loadConfig(int importMode) {
        // do nothing
    }

    @Override
    public void saveConfig() {
        // do nothing
    }

    @Override
    protected void registerManagedObjects() {
        // just skip this stuff
    }

    public static void main(String[] args) {
        String address;
        BasicConfigurator.configure();

        if (args.length > 1) {
            address = args[1];
        } else {
            address = "0.0.0.0/1161";
        }

        try {
            Simulator sim = new Simulator();
            sim.address = address;
            sim.init();
            sim.loadConfig(ImportModes.REPLACE_CREATE);
            sim.addShutdownHook();
            sim.getServer().addContext(new OctetString("public"));
            sim.finishInit();
            sim.run();
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex1) {
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

}
