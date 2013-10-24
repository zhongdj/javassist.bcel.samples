package net.madz.lifecycle.demo.standalone;

import net.madz.lifecycle.annotations.LifecycleMeta;
import net.madz.lifecycle.annotations.StateIndicator;
import net.madz.lifecycle.annotations.Transition;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Finish;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Schedule;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Start;

@LifecycleMeta(ServiceableLifecycleMeta.class)
public class ServiceOrder implements IServiceOrder {

    private String state;

    @Override
    @Transition(Schedule.class)
    public void allocateResources(final long summaryPlanId, final long truckResourceId, final long plangResourceId) {
        long x = 0;
        long y = x;
        long z = 0L;
    }

    @Override
    @Transition(Start.class)
    public void confirmStart() {
        System.out.println("confirmStart ...");
    }

    @Override
    @Transition(Finish.class)
    public void confirmFinish() {
        System.out.println("confirmFinish ...");
    }

    @Override
    @StateIndicator
    public String getServiceOrderState() {
        return state;
    }
}
