package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Component;
import com.qualcomm.ftcrobotcontroller.opmodes.NormalServo;
import com.qualcomm.ftcrobotcontroller.opmodes.TankOpMode;
import com.qualcomm.ftcrobotcontroller.opmodes.components.TankDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.ServoController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by winterst on 11/21/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@interface timed_state {
    double duration();

}

public class StatefulAutonomous extends TankOpMode {
    boolean stateRan = false;
    boolean initial_call = !stateRan;
    double startTime;
    double endTime;
    List<Method> states = new ArrayList<Method>();
    List<Double> durations = new ArrayList<Double>();

    @Override
    public void init() {
        super.init();

        for (Method method : getClass().getDeclaredMethods()) {
            timed_state annotation = method.getAnnotation(timed_state.class);
            states.add(method);
            durations.add(annotation.duration());
            System.out.println(states.toString());
        }

    }

    @Override
    public void loop() {

        initial_call = !stateRan;
        if (initial_call && !states.isEmpty()) {
            stateRan = true;
            startTime = System.currentTimeMillis();
            endTime = startTime + (durations.get(0)*1000);
        }
        if (!states.isEmpty()) {
            telemetry.addData("State", states.get(0).getName());
            telemetry.addData("Duration", durations.get(0));
            runState(endTime);
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

    public void runState(double endTime) {
        if (System.currentTimeMillis() >= endTime) {
            states.remove(0);
            durations.remove(0);
            stateRan = false;
        } else {
            try {
                states.get(0).invoke(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }

        }
    }
}

