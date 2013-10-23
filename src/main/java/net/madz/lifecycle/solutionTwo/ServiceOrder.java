package net.madz.lifecycle.solutionTwo;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.madz.lifecycle.annotations.LifecycleMeta;
import net.madz.lifecycle.annotations.StateIndicator;
import net.madz.lifecycle.annotations.Transition;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Finish;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Schedule;
import net.madz.lifecycle.demo.standalone.ServiceableLifecycleMeta.Transitions.Start;

@LifecycleMeta(ServiceableLifecycleMeta.class)
public class ServiceOrder implements IServiceOrder {

    @Override
    @Transition(Schedule.class)
    public void allocateResources(final long summaryPlanId, final long truckResourceId, final long plangResourceId) {
        final LifecycleExecutor exec = new LifecycleExecutor();
        Method method = findMethod("allocateResources", new Class[] { Long.class, Long.class, Long.class });
        exec.execute(method, new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                allocateResources$Impl(summaryPlanId, truckResourceId, plangResourceId);
                return null;
            }
        });
    }

    protected Method findMethod(String string, Class<?>[] classes) {
        return null;
    }

    protected void allocateResources$Impl(final long summaryPlanId, final long truckResourceId,
            final long plangResourceId) {
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
