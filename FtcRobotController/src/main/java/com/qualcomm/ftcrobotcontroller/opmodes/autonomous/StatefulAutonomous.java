package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Component;
import com.qualcomm.ftcrobotcontroller.opmodes.TankOpMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by winterst on 11/21/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@interface timed_state {
    double duration();
    String next_state() default "";
    boolean first() default false;

}

public class StatefulAutonomous extends TankOpMode {
    boolean stateRan = false;
    boolean initial_call = !stateRan;
    double startTime;
    double endTime;
    Method state;
    @Override
    public void init() {
        super.init();
        boolean hasFirst = false;
        for (Method method : getClass().getDeclaredMethods()) {
            timed_state annotation = method.getAnnotation(timed_state.class);
            if (annotation.first()) {
                if (hasFirst)
                    throw new RuntimeException("Two first autonomous states declared");
                state = method;
                hasFirst = true;
                break;
            }

        }

    }

    @Override
    public void loop() {

        initial_call = !stateRan;
        if (initial_call && state != null) {
            stateRan = true;
            startTime = System.currentTimeMillis();
            endTime = startTime + (state.getAnnotation(timed_state.class).duration()*1000);
        }
        if (state != null) {
            telemetry.addData("State", state.getName());
            telemetry.addData("Duration", state.getAnnotation(timed_state.class).duration());
            try {
                runState(endTime);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            initial_call = false;
            stateRan = true;
            telemetry.addData("State", "Finished");
        }
        for (Component component : components)
        {
            component.doit();
        }
    }
    public void runState(double endTime) throws NoSuchMethodException {
        if (System.currentTimeMillis() >= endTime) {
            stateRan = false;
            next_state(state.getAnnotation(timed_state.class).next_state());
        } else {
            try {
                state.invoke(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }

        }
    }

    public void next_state(String next_state) throws NoSuchMethodException {
        state = getClass().getDeclaredMethod(next_state);
        stateRan = false;

    }
}

