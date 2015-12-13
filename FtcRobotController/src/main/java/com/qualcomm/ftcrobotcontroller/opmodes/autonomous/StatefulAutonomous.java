package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Component;
import com.qualcomm.ftcrobotcontroller.opmodes.NormalServo;
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

public class StatefulAutonomous extends OpMode {
    boolean stateRan = false;
    boolean initial_call = !stateRan;
    double startTime;
    double endTime;
    List<Method> states = new ArrayList<Method>();
    List<Double> durations = new ArrayList<Double>();
    public DcMotor[] rightMotors = new DcMotor[2];
    DcMotor[] leftMotors = new DcMotor[2];

    ServoController servoController;

    NormalServo leftEar;
    NormalServo rightEar;

    public TankDrive tank;

    NormalServo winchServo;
    DcMotor winchMotor;
    List<Component> components = new ArrayList<Component>();
    GyroSensor gyro;
    public void init() {
        leftMotors[0] = hardwareMap.dcMotor.get("motor_1");
        leftMotors[1] = hardwareMap.dcMotor.get("motor_2");
        rightMotors[0] = hardwareMap.dcMotor.get("motor_3");
        rightMotors[1] = hardwareMap.dcMotor.get("motor_4");


        leftMotors[0].setDirection(DcMotor.Direction.REVERSE);
        leftMotors[1].setDirection(DcMotor.Direction.REVERSE);

        //SERVO CONTROLLER
        servoController = hardwareMap.servoController.get("servo_cnrtl");

        //DUMBO EARS
        leftEar = new NormalServo(servoController, 2);
        components.add(leftEar);
        rightEar = new NormalServo(servoController, 6);
        components.add(rightEar);

        winchMotor = hardwareMap.dcMotor.get("winch_motor");

        winchServo = new NormalServo(hardwareMap.servoController.get("servo_cnrtl"), 1);
        components.add(winchServo);

        tank = new TankDrive(rightMotors, leftMotors);
        components.add(tank);

        for (Method method : getClass().getDeclaredMethods()) {
            timed_state annotation = method.getAnnotation(timed_state.class);
            states.add(method);
            durations.add(annotation.duration());
            System.out.println(states.toString());
        }

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();

        leftEar.setLocation(1);
        rightEar.setLocation(-1);

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

