package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.DcMotor;


public class Drive implements Arcade.Component
{
    DcMotor rightMotor, leftMotor;
    float rightMotorSpeed, leftMotorSpeed;
    public Drive(DcMotor rightMotor, DcMotor leftMotor)
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
        System.out.println("Doing it");
        rightMotor.setPower(rightMotorSpeed);
        leftMotor.setPower(leftMotorSpeed);

        rightMotor.setPower(0);
        leftMotor.setPower(0);
    }
}