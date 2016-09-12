package exercise1;

import org.snmp4j.PDU;
import org.snmp4j.Session;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GetSysDescrForever {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GetSysDescrForever.class);

    public GetSysDescrForever() {
        System.out.println();
    }

    @Monitor
    public ResponseEvent run(Session session, Target target) throws Exception {
        return getSysDescr(session, target);
    }

    private ResponseEvent getSysDescr(Session session, Target target) throws IOException {
        return session.send(createGetRequestPDU(), target);
    }

    private PDU createGetRequestPDU() {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.1.0"))); // sysDescr
        pdu.setType(PDU.GET);
        return pdu;
    }
}
