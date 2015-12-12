package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Component;
import com.qualcomm.ftcrobotcontroller.opmodes.NormalServo;
import com.qualcomm.ftcrobotcontroller.opmodes.components.TankDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

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
    String nextState() default "";
    boolean first() default false;

}

public class StatefulAutonomous extends OpMode {
    boolean stateRan = false;
    boolean initial_call = !stateRan;
    double startTime;
    double endTime;
    List<Method> states = new ArrayList<Method>();
    List<Double> durations = new ArrayList<Double>();
    public DcMotor[] rightMotors = new DcMotor[2];
    DcMotor[] leftMotors = new DcMotor[2];
    public TankDrive tank;

    NormalServo winchServo;
    NormalServo leftEar;

    DcMotor winchMotor;
    List<Component> components = new ArrayList<Component>();
    GyroSensor gyro;
    public void init() {

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();


        leftMotors[0] = hardwareMap.dcMotor.get("motor_1");
        leftMotors[1] = hardwareMap.dcMotor.get("motor_2");
        rightMotors[0] = hardwareMap.dcMotor.get("motor_3");
        rightMotors[1] = hardwareMap.dcMotor.get("motor_4");


        leftMotors[0].setDirection(DcMotor.Direction.REVERSE);
        leftMotors[1].setDirection(DcMotor.Direction.REVERSE);

        tank = new TankDrive(rightMotors, leftMotors, gyro, telemetry);

        winchMotor = hardwareMap.dcMotor.get("winch_motor");

        winchServo = new NormalServo(hardwareMap.servoController.get("servo_cnrtl"), 1);
        components.add(winchServo);

        components.add(tank);

        Method methods[] = getClass().getDeclaredMethods();
        for (Method method : methods) {
            timed_state annotation = method.getAnnotation(timed_state.class);
            if(annotation.first())
            {
                states.add(method);
                durations.add(annotation.duration());
                break;
            }
        }
        for(Method method: states)
        {
            timed_state annotation = method.getAnnotation(timed_state.class);
            String next_state = annotation.nextState();
            for(Method _method : methods)
            {
                timed_state _annotation = method.getAnnotation(timed_state.class);
                if (_method.getName() == next_state && next_state!="")
                {

                    states.add(_method);
                    durations.add(_annotation.duration());
                }
                else
                {
                    break;
                }

            }
        }


    }

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
            telemetry.addData("speed", tank.getSpeed());
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

