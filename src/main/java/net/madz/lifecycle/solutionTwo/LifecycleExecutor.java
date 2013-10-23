package net.madz.lifecycle.solutionTwo;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class LifecycleExecutor {

    public <V> V execute(Method m, Callable<V> callable) {
        try {
            preExe(m);
            V result = callable.call();
            postExe(m);
            return result;
        } catch (Exception e) {
            handleException(e);
            throw new IllegalStateException(e);
        } finally {
            doFinally();
        }
    }

    protected void doFinally() {
    }

    protected void handleException(Throwable t) {
    }

    protected void postExe(Method m) {
    }

    protected void preExe(Method m) {
    }
}