package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.opmodes.reference.Component;
import com.qualcomm.robotcore.hardware.DcMotor;


public class ArcadeDrive implements Component
{
    DcMotor rightMotor, leftMotor;
    float rightMotorSpeed, leftMotorSpeed;

    public ArcadeDrive(DcMotor rightMotor, DcMotor leftMotor)
    {
        this.rightMotor = rightMotor;
        this.leftMotor = leftMotor;
    }
    public void move(float x, float y)
    {
        rightMotorSpeed = y - x;
        leftMotorSpeed  = y + x;
    }

    public void doit()
    {
        rightMotor.setPower(rightMotorSpeed);
        leftMotor.setPower(leftMotorSpeed);

        rightMotor.setPower(0);
        leftMotor.setPower(0);
    }
}