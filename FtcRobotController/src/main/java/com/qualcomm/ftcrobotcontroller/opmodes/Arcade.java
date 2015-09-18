package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

/**
 * Created by winterst on 9/12/15.
 */
public class Arcade extends OpMode{
    DcMotor motorRight;
    DcMotor motorLeft;
    Drive drive;
    ServoController servoController;
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
        servoController = hardwareMap.servoController.get("Servo Controller 1");
        servoController.pwmEnable();

    	//motorLeft.setDirection(DcMotor.Direction.REVERSE);
    	//drive = new Drive(motorRight, motorLeft);
        servoTest = new ServoTest(servoController);
    }

    public void loop()
    {
        //drive.move(gamepad1.left_stick_x, gamepad1.left_stick_y);

        if(gamepad1.a)
        {
            servoTest.increase();
        }
        else if(gamepad1.b)
        {
            servoTest.decrease();
        }
        servoTest.doit();
        //for( Component component: components)
        //{
         //   System.out.println("Debug");
          //  component.doit();
        //}
    }

}
