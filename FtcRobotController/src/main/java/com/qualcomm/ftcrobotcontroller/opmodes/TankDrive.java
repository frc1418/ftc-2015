package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class TankDrive {

    DcMotor[] leftMotors;
    DcMotor[] rightMotors;

    float motorSpeedConstant = 1;

    public TankDrive(DcMotor[] leftMotors, DcMotor[] rightMotors){
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
    }

    public void drive (float y1, float y2){
        for(DcMotor motor : leftMotors){
            motor.setPower((y1*motorSpeedConstant));
        }

        for(DcMotor motor : rightMotors){
            motor.setPower((y2*motorSpeedConstant));
        }
    }


}
