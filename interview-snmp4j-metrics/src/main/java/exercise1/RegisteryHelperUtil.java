package exercise1;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

public class RegisteryHelperUtil {

    private static RegisteryHelperUtil helperUtil = new RegisteryHelperUtil();

    private Counter noOfReq;
    private Counter noOfTimeOuts;

    private RegisteryHelperUtil() {
    }

    public static RegisteryHelperUtil getInstance() {
        return helperUtil;
    }

    public void registerMetrics(MetricRegistry metricRegistry) {
        noOfReq = new Counter();
        metricRegistry.register("No.of.req", noOfReq);
        noOfTimeOuts = new Counter();
        metricRegistry.register("No.of.timeouts", noOfTimeOuts);
    }

    public Counter getNoOfReq() {
        return noOfReq;
    }

    public Counter getNoOfTimeOuts() {
        return noOfTimeOuts;
    }
}
