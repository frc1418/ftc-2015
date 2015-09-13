package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by winterst on 9/12/15.
 */
public class Arcade extends OpMode{
    DcMotor motorRight;
    DcMotor motorLeft;
    Drive drive;
    interface Component {
        void  doit();
    }
    Component [] components = {drive
    };

    public void init()
    {
    	motorRight = hardwareMap.dcMotor.get("motor_2");
    	motorLeft = hardwareMap.dcMotor.get("motor_1");
    	motorLeft.setDirection(DcMotor.Direction.REVERSE);
    	drive = new Drive(motorRight, motorLeft);



    }

    public void loop()
    {
        drive.move(gamepad1.left_stick_x, gamepad1.left_stick_y);
        for( Component component: components)
        {
            component.doit();
        }
    }

}
