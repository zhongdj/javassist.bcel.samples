package net.madz.lifecycle.solutionThree;

import java.lang.reflect.Method;

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
    // try {
    // Method m = findMethod("allocateResources", new Class[] { Long.class,
    // Long.class, Long.class });
    // preExe(m);
    // synchronized (this) {
    // allocateResources$Impl(summaryPlanId, truckResourceId, plangResourceId);
    // }
    // postExe(m);
    // } catch (Throwable t) {
    // handleException(t);
    // } finally {
    // doFinally();
    // }
    // }
    @Override
    @Transition(Schedule.class)
    public void allocateResources(final long summaryPlanId, final long truckResourceId, final long plangResourceId) {
        System.out.println("Calling method: original allocateResources ... ");
    }

    protected Method findMethod(String string, Class<?>[] classes) {
        return null;
    }

    protected void handleException(Throwable t) {
        System.out.println("intercepting with :" + getClass().getName() + " @handleException");
    }

    protected void cleanup() {
        System.out.println("intercepting with :" + getClass().getName() + " @cleanup");
    }

    protected void postExe(Method m) {
        System.out.println("intercepting with :" + getClass().getName() + " @postExec");
    }

    protected void preExe(Method m) {
        System.out.println("intercepting with :" + getClass().getName() + " @preExec");
    }

    @Override
    @Transition(Start.class)
    public void confirmStart() {
        // TODO Auto-generated method stub
    }

    @Override
    @Transition(Finish.class)
    public void confirmFinish() {
        // TODO Auto-generated method stub
    }

    @Override
    @StateIndicator
    public String getServiceOrderState() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {
        ServiceOrder order = new ServiceOrder();
        order.allocateResources(1L, 2L, 3L);
    }
}
