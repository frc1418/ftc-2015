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
    float rotation = 0.0f;

    public TankDrive(DcMotor[] leftMotors, DcMotor[] rightMotors, GyroSensor gyro) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;

        this.leftSpeed = 0;
        this.rightSpeed = 0;

        this.gyro = gyro;
    }

    public void move(float moveValue, float rotateValue) {
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {

                leftSpeed = moveValue - rotateValue;
                rightSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftSpeed = Math.max(moveValue, -rotateValue);
                rightSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftSpeed = -Math.max(-moveValue, rotateValue);
                rightSpeed = moveValue + rotateValue;
            } else {
                leftSpeed = moveValue - rotateValue;
                rightSpeed = -Math.max(-moveValue, -rotateValue);
            }

        }
    }

    public float angleRotation(int target_angle) {
        float rotation = 0.0f;
        int angleOffset = target_angle - gyro.getHeading();
        boolean invert = false;
        if (target_angle > 180) {
            target_angle -= 360;
        }
        if (angleOffset < -1 || angleOffset > 1) {
            rotation = (float) (angleOffset * angle_constant);
            rotation = (float) (Math.max(Math.min(0.3, rotation), -0.3));
        }
        return rotation;

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
