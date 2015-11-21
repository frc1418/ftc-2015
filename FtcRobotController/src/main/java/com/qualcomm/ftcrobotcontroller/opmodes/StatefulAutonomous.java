package com.qualcomm.ftcrobotcontroller.opmodes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by winterst on 11/21/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@interface timed_state {
    double duration();

}

public class StatefulAutonomous {
    boolean stateRan = false;
    boolean initial_call = !stateRan;
    double startTime;
    double endTime;
    List<Method> states = new ArrayList<Method>();
    List<Double> durations = new ArrayList<Double>();
    public void onEnable() {
        for (Method method : getClass().getMethods()) {
            timed_state annotation = method.getAnnotation(timed_state.class);
            states.add(method);
            durations.add(annotation.duration());
        }
    }

    public void loop() {
        initial_call = !stateRan;
        if (initial_call)
        {
            stateRan = true;
            startTime = System.currentTimeMillis();
            endTime = startTime+durations.get(0);
        }
        runState(endTime);
    }

    public void runState(double endTime) {
        if (System.currentTimeMillis() >= endTime) {
            states.remove(0);
            durations.remove(0);
            stateRan = false;
        } else
        {
            try {
                states.get(0).invoke(getClass());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}

