package com.qualcomm.ftcrobotcontroller.opmodes.components;

import com.qualcomm.ftcrobotcontroller.opmodes.Component;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.robocol.Telemetry;

public class TankDrive implements Component {

    DcMotor[] leftMotors;
    DcMotor[] rightMotors;

    private boolean reverse = false;

    private float speedVariable = 1;

    public float rightSpeed;
    public  float leftSpeed;

    GyroSensor gyro;
    double angle_constant = .04;
    public float rotation = 0.0f;
    public int target, heading;

    Telemetry telemetry;
    public TankDrive(DcMotor[] leftMotors, DcMotor[] rightMotors, GyroSensor gyro, Telemetry telemetry) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;

        this.leftSpeed = 0;
        this.rightSpeed = 0;

        this.gyro = gyro;

        this.telemetry = telemetry;
    }

   /*public void move(float moveValue, float rotateValue) {
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {

                leftSpeed = moveValue - rotateValue; //1-.6 = .6
                rightSpeed = Math.max(moveValue, rotateValue); // 1,.3 = 1
            } else {
                leftSpeed = Math.max(moveValue, -rotateValue);//1, -.5 = 1
                rightSpeed = moveValue + rotateValue; //1-.5 = .5
            }
        } else {
            if (rotateValue > 0.0) {
                leftSpeed = -Math.max(-moveValue, rotateValue); // 1, .5 = -1
                rightSpeed = moveValue + rotateValue; // -1+.5 = -.5
            } else {
                leftSpeed = moveValue - rotateValue;
                rightSpeed = -Math.max(-moveValue, -rotateValue);
            }

        }
        telemetry.addData("Left Speed", rightSpeed);
        telemetry.addData("Right Speed", leftSpeed);
    }*/

    public void move(float moveValue, float rotateValue) {
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftSpeed = moveValue - rotateValue;
                rightSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftSpeed = Math.max(moveValue, -rotateValue);
                rightSpeed = moveValue + rotateValue;
            }
        }
            else {
            if (rotateValue > 0.0) {
                leftSpeed = -Math.max(-moveValue, rotateValue);
                rightSpeed = moveValue + rotateValue;
            } else {
                leftSpeed = moveValue - rotateValue;
                rightSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
            telemetry.addData("Left Speed", rightSpeed);
            telemetry.addData("Right Speed", leftSpeed);
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
