package exercise1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.snmp4j.event.ResponseEvent;

@Aspect
public class RequestMonitor {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestMonitor.class);

    public RequestMonitor() {
        System.out.println();
    }

    @Around("@annotation(Monitor)")
    public Object performOperation(ProceedingJoinPoint pjp) throws Throwable {
        try {
            ResponseEvent response = (ResponseEvent) pjp.proceed();
            RegisteryHelperUtil.getInstance().getNoOfReq().inc();
            if (response.getResponse() == null) {
                RegisteryHelperUtil.getInstance().getNoOfTimeOuts().inc();
            } else {
                System.out.println(response.getResponse().toString());
            }
            return response;
        } catch (Exception e) {

        }
        return null;
    }
}
