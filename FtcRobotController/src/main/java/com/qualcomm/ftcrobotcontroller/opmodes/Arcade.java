package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by winterst on 9/12/15.
 */
public class Arcade extends OpMode{
    DcMotor motorRight;
    DcMotor motorLeft;
    Drive drive;
    Servo servo;
    ServoTest servoTest;
    interface Component {
        void  doit();
    }
    Component [] components = {servoTest
    };

    public void init()
    {
    	//motorRight = hardwareMap.dcMotor.get("motor_2");
    	//motorLeft = hardwareMap.dcMotor.get("motor_1");
        servo = hardwareMap.servo.get("servo_1");
    	//motorLeft.setDirection(DcMotor.Direction.REVERSE);
    	//drive = new Drive(motorRight, motorLeft);
        servoTest = new ServoTest(servo);
    }

    public void loop()
    {
        //drive.move(gamepad1.left_stick_x, gamepad1.left_stick_y);

        if(gamepad1.a)
        {
            System.out.println("Hello");
            servo.setDirection(Servo.Direction.FORWARD);
        }
        else if(gamepad1.b)
        {
            System.out.println("Debug");
            servo.setDirection(Servo.Direction.REVERSE);
        }
        servoTest.doit();
        //for( Component component: components)
        //{
         //   System.out.println("Debug");
          //  component.doit();
        //}
    }

}
