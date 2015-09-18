package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.Range;

import java.text.DecimalFormat;

/**
 * Created by winterst on 9/16/15.
 */
public class ServoTest implements Arcade.Component{
    ServoController servoController;
    double location = .5;
    DecimalFormat df;
    public ServoTest(ServoController servoController)
    {
        this.servoController = servoController;

    }
    public void increase()
    {
        location+=.01;
    }
    public void decrease()
    {
        location-=.01;
    }

    public void doit()
    {
        System.out.println(location);
        location = Math.min(Math.max(0, location), 1);
        servoController.setServoPosition(1,  location);
    }
}
