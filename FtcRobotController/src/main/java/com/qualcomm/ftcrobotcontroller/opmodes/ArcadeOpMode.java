package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class Arcade extends OpMode{
    DcMotor motorRight;
    DcMotor motorLeft;
    Drive drive;
    ServoController servoController;
    NormalServo servo1;
    Servo servo;

    Component [] components = {drive
    };

    public void init()
    {
    	motorRight = hardwareMap.dcMotor.get("motor_2");
    	motorLeft = hardwareMap.dcMotor.get("motor_1");

    	motorLeft.setDirection(DcMotor.Direction.REVERSE);

    	drive = new Drive(motorRight, motorLeft);
    	*/

        servoController = hardwareMap.servoController.get("Servo Controller 1");
        servoController.pwmEnable();

        //servo = new Servo(servoController, 1);



        servo1 = new NormalServo(servoController, 1);

    }

    public void loop()
    {
        //drive.move(gamepad1.left_stick_x, gamepad1.left_stick_y);

        if(gamepad1.a)
        {
            /*SVH
            servo.setDirection(Servo.Direction.FORWARD);
            */
            servo1.increase();
        }
        else if(gamepad1.b)
        {
            /*SVH
            servo.setDirection(Servo.Direction.REVERSE);
            */
            servo1.decrease();
        }
        //servo.setDirection(0);
        servo1.doit();
        /*for( Component component: components)
        {
            System.out.println("Debug");
            component.doit();
        }*/
    }

}
