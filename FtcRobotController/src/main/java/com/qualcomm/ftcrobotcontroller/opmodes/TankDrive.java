package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class TankDrive implements Component
{

    DcMotor[] leftMotors;
    DcMotor[] rightMotors;

    private boolean reverse = false;

    private float speedVariable = 1;

    private float rightSpeed;
    private float leftSpeed;

    public TankDrive(DcMotor[] leftMotors, DcMotor[] rightMotors)
    {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;

        this.leftSpeed = 0;
        this.rightSpeed = 0;
    }

    public void move(float rightY, float leftY)
    {
        if (reverse)
        {
            leftSpeed = -(rightY * speedVariable);
            rightSpeed = -(leftY * speedVariable);
        }
        else
        {
            leftSpeed = (leftY * speedVariable);
            rightSpeed = (rightY * speedVariable);
        }
    }

    public void reverse()
    {
        reverse = !reverse;
    }

    public boolean isReverse()
    {
        return reverse;
    }

    public float getSpeedVariable()
    {
        return speedVariable;
    }

    public void setSpeedVariable(float speedVariable)
    {
        this.speedVariable = speedVariable;
    }

    public void doit()
    {
        for (DcMotor motor : leftMotors)
        {
            motor.setPower(leftSpeed);
        }
        for (DcMotor motor : rightMotors)
        {
            motor.setPower(rightSpeed);
        }
    }
}
