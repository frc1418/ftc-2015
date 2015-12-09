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

    GyroSensor gyro;
    double angle_constant = .040;
    public TankDrive(DcMotor[] leftMotors, DcMotor[] rightMotors, GyroSensor gyro) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;

        this.leftSpeed = 0;
        this.rightSpeed = 0;

        this.gyro = gyro;
    }

    public void move(float rightY, float leftY) {
        //System.out.println("Right speed: "+rightY);
        if (reverse) {
            leftSpeed = -(rightY * speedVariable);
            rightSpeed = -(leftY * speedVariable);
        } else {
            leftSpeed = (leftY * speedVariable);
            rightSpeed = (rightY * speedVariable);
        }
    }

    public void angleRotation(int target_angle) {
        double rotation = 0.0;
        int angleOffset = target_angle - gyro.getHeading();

        if (angleOffset < -1 || angleOffset > 1) {
            rotation = angleOffset * angle_constant;
            rotation = Math.max(Math.min(0.3, rotation), -0.3);
        }
        if(rotation > 0.0)
        {
            leftSpeed = (float) rotation/2;
            rightSpeed = (float) (1-(rotation));
        }
        if(rotation < 0.0)
        {
            leftSpeed = (float) -rotation/2;
            rightSpeed = (float) rotation/2;
        }

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
            System.out.println("AJDFJSLFDSJFDSFDSFSDFDSFDSFDSF");
            motor.setPower(leftSpeed);
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(rightSpeed);
        }
        rightSpeed = 0;
        leftSpeed = 0;
    }
}
