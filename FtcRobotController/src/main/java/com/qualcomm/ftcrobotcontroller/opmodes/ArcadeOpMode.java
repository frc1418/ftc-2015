package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import java.util.ArrayList;
import java.util.List;

public class ArcadeOpMode extends OpMode{
    DcMotor motorRight;
    DcMotor motorLeft;
    ArcadeDrive drive;

    ServoController servoController;
    NormalServo servo;

    List<Component> components = new ArrayList<Component>();

    public void init()
    {
    	motorRight = hardwareMap.dcMotor.get("motor_2");
    	motorLeft = hardwareMap.dcMotor.get("motor_1");

    	motorLeft.setDirection(DcMotor.Direction.REVERSE);

    	drive = new ArcadeDrive(motorRight, motorLeft);
        components.add(drive);

        servoController = hardwareMap.servoController.get("Servo Controller 1");
        servoController.pwmEnable();

        servo = new NormalServo(servoController);
        components.add(servo);

    }

    public void loop() {
        drive.move(gamepad1.left_stick_x, gamepad1.left_stick_y);

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
