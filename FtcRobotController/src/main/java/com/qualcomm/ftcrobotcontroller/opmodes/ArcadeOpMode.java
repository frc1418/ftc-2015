package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class ArcadeOpMode extends OpMode{
    DcMotor motorRight;
    DcMotor motorLeft;
    ArcadeDrive drive;

    Component [] components = {drive
    };

    public void init()
    {
    	motorRight = hardwareMap.dcMotor.get("motor_2");
    	motorLeft = hardwareMap.dcMotor.get("motor_1");

    	motorLeft.setDirection(DcMotor.Direction.REVERSE);

    	drive = new ArcadeDrive(motorRight, motorLeft);

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
