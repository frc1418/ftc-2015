package com.qualcomm.ftcrobotcontroller.opmodes.UnitTests;

/**
 * Created by winterst on 12/11/15.
 */
import java.util.Arrays;
import java.util.Random;

public class GyroTest extends Thread
{

    int headings[] = new int[10];
    int targetAngle[] = new int[10];
    Gyro gyro;
    TankDrive tank;
    public static void main(String[] args)
    {
        GyroTest test = new GyroTest();
        test.start();
    }

    public GyroTest()
    {
        Random random = new Random();

        gyro = new Gyro();
        for(int i = 0; i< 10; i++)
        {
            headings[i] = random.nextInt(360);
            targetAngle[i] = random.nextInt(360);
        }
        tank = new TankDrive(gyro);
    }

    public void run()
    {
        int counter = -1;
        while(counter <9)
        {
            counter++;
            gyro.setHeading(headings[counter]);
            tank.move(1, tank.angleRotation(targetAngle[counter]));

            int angleOffset = targetAngle[counter] > gyro.getHeading() ? targetAngle[counter] - gyro.getHeading() : gyro.getHeading() - targetAngle[counter];
            angleOffset%=360;
            angleOffset = angleOffset > 180 ? 180 - angleOffset : angleOffset;
            if (gyro.getHeading() + angleOffset != targetAngle[counter])
                angleOffset*=-1;
            System.out.println(angleOffset);
            if (angleOffset < 0)
            {
                assert(tank.leftSpeed>tank.rightSpeed);
            }
            else
            {
                assert(tank.leftSpeed<tank.rightSpeed);
            }
        }
        System.out.println("Done");

    }

}

class TankDrive{

    private boolean reverse = false;

    private float speedVariable = 1;

    public float rightSpeed;
    public  float leftSpeed;

    Gyro gyro;
    double angle_constant = .04;
    public float rotation = 0.0f;
    public int target, heading;

    public TankDrive(Gyro gyro) {

        this.leftSpeed = 0;
        this.rightSpeed = 0;

        this.gyro = gyro;

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
    }
    public float angleRotation(int target_angle) {
        int angleOffset = target_angle > gyro.getHeading() ? target_angle - gyro.getHeading() : gyro.getHeading() - target_angle;
        angleOffset%=360;
        angleOffset = angleOffset > 180 ? 180 - angleOffset : angleOffset;
        if (gyro.getHeading() + angleOffset != target_angle)
            angleOffset*=-1;
        if (angleOffset < -1 || angleOffset > 1) {
            rotation = (float) (angleOffset * angle_constant);
            rotation = (float) (Math.max(Math.min(0.3, rotation), -0.3));
        }
        System.out.println(gyro.getHeading()+", "+target_angle +", "+angleOffset+", "+rotation);
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
}

class Gyro
{
    private int heading;

    public Gyro()
    {

    }
    public int getHeading()
    {
        return heading;
    }

    public void setHeading(int heading)
    {
        this.heading = heading;
    }

}


