package net.madz.lifecycle.solutionOne;

import java.util.concurrent.Callable;

import net.madz.lifecycle.annotations.LifecycleMeta;
import net.madz.lifecycle.annotations.StateIndicator;
import net.madz.lifecycle.annotations.Transition;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Finish;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Schedule;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Start;

@LifecycleMeta(ServiceableLifecycleMeta.class)
public class ServiceOrder implements IServiceOrder {

    // @Override
    // @Transition(Schedule.class)
    // public void allocateResources(final long summaryPlanId, final long
    // truckResourceId, final long plangResourceId) {
    // }
    @Override
    @Transition(Schedule.class)
    public void allocateResources(final long summaryPlanId, final long truckResourceId, final long plangResourceId) {
        final InterceptorController<Void> controller = new InterceptorController<Void>();
        final InterceptContext<Void> context = new InterceptContext<Void>(getClass(), this, "allocateResources",
                new Class[] { Long.class, Long.class, Long.class });
        controller.exec(context, new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                allocateResources$Impl(summaryPlanId, truckResourceId, plangResourceId);
                return null;
            }
        });
    }

    protected void allocateResources$Impl(final long summaryPlanId, final long truckResourceId,
            final long plangResourceId) {
        long x = 0;
        long y = x;
        long z = 0L;
    }

    @Override
    @Transition(Start.class)
    public void confirmStart() {
    }

    @Override
    @Transition(Finish.class)
    public void confirmFinish() {
    }

    @Override
    @StateIndicator
    public String getServiceOrderState() {
        return null;
    }

}
