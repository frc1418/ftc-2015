package com.qualcomm.ftcrobotcontroller.opmodes.components;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Winch {
    DcMotor winch_motor;
    int speed = 0;

    public Winch(DcMotor winch_motor){
        this.winch_motor = winch_motor;
    }

    public void setSpeed(){
        winch_motor.setPower(1);
    }
}
