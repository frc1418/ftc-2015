package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.opmodes.Component;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import java.util.ArrayList;
import java.util.List;

public class ArcadeOpMode extends OpMode{
    DcMotor motorRight;
    DcMotor motorLeft;

    ServoController servoController;
    NormalServo servo;

    public GyroSensor gyro;

    List<Component> components = new ArrayList<Component>();
    ArcadeDrive drive;

    public void init()
    {
    	motorRight = hardwareMap.dcMotor.get("motor_2");
    	motorLeft = hardwareMap.dcMotor.get("motor_1");

    	motorLeft.setDirection(DcMotor.Direction.REVERSE);

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        while(gyro.isCalibrating())
        {
            try {
                wait(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    	drive = new ArcadeDrive(motorRight, motorLeft, gyro);
        components.add(drive);

        servoController = hardwareMap.servoController.get("Servo Controller 1");
        servoController.pwmEnable();

        servo = new NormalServo(servoController, 1);
        components.add(servo);



    }

    public void loop() {
        drive.move(gamepad1.left_stick_y, gamepad1.left_stick_x);

        if(gamepad1.a)
        {
            servo.increase();
        }
        else if(gamepad1.b)
        {
            servo.decrease();
        }

        for(int i=0; i< components.size(); i++){
            System.out.println();
            components.get(i).doit();
        }
    }

}
