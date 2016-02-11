package com.qualcomm.ftcrobotcontroller.opmodes.components;

import com.qualcomm.ftcrobotcontroller.opmodes.Component;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

public class TankDrive implements Component {

    DcMotor[] leftMotors;
    DcMotor[] rightMotors;

    private boolean reverse = false;

    private float speedVariable = 1;

    private float rightSpeed;
    private float leftSpeed;

    double angle_constant = .040;

    GyroSensor gyro;

    float rotation;
    public TankDrive(DcMotor[] leftMotors, DcMotor[] rightMotors, GyroSensor gyro) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;

        this.gyro = gyro;

        this.leftSpeed = 0;
        this.rightSpeed = 0;
    }

    public void move(float leftY, float rightY) {
        //System.out.println("Right speed: "+rightY);
        if (reverse) {
            leftSpeed = -(rightY * speedVariable);
            rightSpeed = -(leftY * speedVariable);
        } else {
            leftSpeed = (leftY * speedVariable);
            rightSpeed = (rightY * speedVariable);
        }
    }

    public boolean angleRotation(int target_angle)
    {
        float angleOffset = target_angle - getGyroAngle();

        if (angleOffset < -1 || angleOffset > 1) {
            rotation = angleOffset * .04f;
            rotation = Math.max(Math.min(.8f, rotation), -.8f);
            this.leftSpeed = rotation;
            this.rightSpeed = -rotation;
            return false;
        }

        return true;


    }

    public int getGyroAngle()
    {
        return gyro.getHeading() <= 180 ? gyro.getHeading() : gyro.getHeading() - 360;
    }

    public void reverse() {
        reverse = !reverse;
    }

    public boolean isReverse() {
        return reverse;
    }

    public float getSpeedVariable() {
        return speedVariable;
    }

    public void setSpeedVariable(float speedVariable) {
        this.speedVariable = speedVariable;
    }

    public float getSpeed() {
        return rightSpeed;
    }

    public void doit() {

        for (DcMotor motor : leftMotors) {
            motor.setPower(leftSpeed);
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(rightSpeed);
        }
        rightSpeed = 0;
        leftSpeed = 0;
    }
}
