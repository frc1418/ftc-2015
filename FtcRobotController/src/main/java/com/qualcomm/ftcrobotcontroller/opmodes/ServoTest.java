package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by winterst on 9/16/15.
 */
public class ServoTest implements Arcade.Component{
    Servo servo;
    Servo.Direction speed;
    public ServoTest(Servo servo)
    {
        this.servo = servo;
    }
    public void moveF()
    {

        speed = Servo.Direction.FORWARD;
    }
    public void moveR()
    {
        speed = Servo.Direction.REVERSE;
    }

    public void doit()
    {
        servo.setDirection(speed);
    }
}
