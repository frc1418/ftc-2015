package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.ftcrobotcontroller.opmodes.components.TankDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.ServoController;

import java.util.ArrayList;
import java.util.List;

public class TankOpMode extends OpMode
{

    GyroSensor gyro;
    ColorSensor rgb;
    DcMotor[] rightMotors = new DcMotor[2];
    DcMotor[] leftMotors = new DcMotor[2];

    ServoController servoController;

    NormalServo winchServo;
    DcMotor winchMotor;

    NormalServo leftEar;
    NormalServo rightEar;


    TankDrive tank;

    List<Component> components = new ArrayList<Component>();

    public void init()
    {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();

        //DRIVING
        leftMotors[0] = hardwareMap.dcMotor.get("motor_1");
        leftMotors[1] = hardwareMap.dcMotor.get("motor_2");
        rightMotors[0] = hardwareMap.dcMotor.get("motor_3");
        rightMotors[1] = hardwareMap.dcMotor.get("motor_4");


        leftMotors[0].setDirection(DcMotor.Direction.REVERSE);
        leftMotors[1].setDirection(DcMotor.Direction.REVERSE);


        tank = new TankDrive(rightMotors, leftMotors, gyro);
        components.add(tank);


        //SERVO CONTROLLER
        servoController = hardwareMap.servoController.get("servo_cnrtl");

        //WINCH
        winchServo = new NormalServo(servoController, 1);
        components.add(winchServo);
        winchMotor = hardwareMap.dcMotor.get("winch_motor");

        //DUMBO EARS
        leftEar = new NormalServo(servoController, 2);
        components.add(leftEar);
        /*UNATTACHED
        rightEar = new NormalServo(servoController, 3);
        components.add(rightEar);
        */

        rgb = hardwareMap.colorSensor.get("rgb");
        rgb.enableLed(true);


        while(gyro.isCalibrating())
        {
            try {
                wait(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loop()
    {
        //REVERSE
        if (gamepad1.a)
        {
            tank.reverse();
        }

        //DRIVE
        tank.move(-gamepad1.left_stick_y, gamepad1.right_stick_x);

        //WINCH
        if (this.gamepad1.dpad_down && !this.gamepad1.dpad_up){
            winchServo.increase();
        }
        else if (this.gamepad1.dpad_up && !this.gamepad1.dpad_down) {
            winchServo.decrease();
        }

        if (!this.gamepad1.dpad_left && this.gamepad1.dpad_right)
        {
            if (gamepad1.x)
            {
                tank.move(0.4f, 0.4f);
            }
            winchMotor.setPower(1);
        }
        else if (!this.gamepad1.dpad_right && this.gamepad1.dpad_left)
        {
            if (gamepad1.x)
            {
                tank.move(0.4f, 0.4f);
            }
            winchMotor.setPower(-1);
        }
        else
        {
            winchMotor.setPower(0);
        }

        if(gamepad1.left_bumper) {
            leftEar.setLocation(0.3);
        }else{
            leftEar.increase();
        }

        /*UN ATTACHED
        if(gamepad1.right_bumper) {
            rightEar.increase();
        }else{
            rightEar.decrease();
        }
        */

        for (Component component : components)
        {
            component.doit();
        }



        //telemetry.addData("Winch Position", winchServo.location);
        //telemetry.addData("Reverse", tank.isReverse());
        telemetry.addData("Gyro Heading", gyro.getHeading());
        telemetry.addData("Color G: ", rgb.green());
        telemetry.addData("Color R: ", rgb.red());
        telemetry.addData("Color B: ", rgb.blue());
        //telemetry.addData("Gyro Rotation", gyro.getRotation());
    }
}
