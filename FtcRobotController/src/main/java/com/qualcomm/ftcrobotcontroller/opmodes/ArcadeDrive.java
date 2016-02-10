package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.opmodes.Component;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;


public class ArcadeDrive implements Component
{
    DcMotor rightMotor, leftMotor;
    float rightMotorSpeed, leftMotorSpeed;
    GyroSensor gyro;
    float y, rotation;
    public ArcadeDrive(DcMotor rightMotor, DcMotor leftMotor, GyroSensor gyro)
    {
        this.rightMotor = rightMotor;
        this.leftMotor = leftMotor;
        this.gyro = gyro;
    }
    public void move(float y, float rotation)
    {
        this.y = y;
        this.rotation = rotation;

    }

    public int getGyroAngle()
    {
        return gyro.getHeading() <= 180 ? gyro.getHeading() : gyro.getHeading() - 360;
    }
    public void angleRotation(int target_angle)
    {
        float angleOffset = target_angle - getGyroAngle();

        if (angleOffset < -1 || angleOffset > 1) {
        rotation = angleOffset * .004f;
        rotation = Math.max(Math.min(.4f, rotation), -.4f);
        }
    }
    public void doit()
    {
        rightMotorSpeed = Math.max(Math.min(y - rotation, 1), -1);
        leftMotorSpeed  = Math.max(Math.min(y + rotation, 1), -1);

        rightMotor.setPower(rightMotorSpeed);
        leftMotor.setPower(leftMotorSpeed);

        rightMotor.setPower(0);
        leftMotor.setPower(0);
    }
}