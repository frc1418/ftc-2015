package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

/**
 * Created by winterst on 9/26/15.
 */
public class FullServo implements Component{
    ServoController servoController;
    int port;
    Servo.Direction direction;
    double location = 0;

    public void doit()
    {

    }
}
