package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.Range;

import java.text.DecimalFormat;

/**
 * Created by winterst on 9/16/15.
 */
public class NormalServo implements Component{
    ServoController servoController;
    double location = .5;
    int port;
    public NormalServo(ServoController servoController, int port)
    {
        this.servoController = servoController;
        this.port = port;

    }
    public void increase()
    {
        location+=.01;
    }
    public void decrease()
    {
        location-=.01;
    }

    public void setLocation (double location){
        this.location = location;
    }

    public void doit()
    {
        System.out.println(location);
        location = Math.min(Math.max(0, location), 1);
        servoController.setServoPosition(port,  location);
        //servoController.set

    }
}
