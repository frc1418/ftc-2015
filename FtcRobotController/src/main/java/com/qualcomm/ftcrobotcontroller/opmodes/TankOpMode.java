package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import java.util.ArrayList;
import java.util.List;

public class TankOpMode extends OpMode
{
    DcMotor[] rightMotors = new DcMotor[2];
    DcMotor[] leftMotors = new DcMotor[2];

    NormalServo winchServo;
    Servo rollerServo;

    DcMotor winchMotor;

    GyroSensor sensorGyro;

    TankDrive tank;

    List<Component> components = new ArrayList<Component>();

    public void init()
    {

        //sensorGyro = hardwareMap.gyroSensor.get("gyro");

        leftMotors[0] = hardwareMap.dcMotor.get("motor_1");
        leftMotors[1] = hardwareMap.dcMotor.get("motor_2");
        rightMotors[0] = hardwareMap.dcMotor.get("motor_3");
        rightMotors[1] = hardwareMap.dcMotor.get("motor_4");


        leftMotors[0].setDirection(DcMotor.Direction.REVERSE);
        leftMotors[1].setDirection(DcMotor.Direction.REVERSE);

        winchMotor = hardwareMap.dcMotor.get("winch_motor");

        winchServo = new NormalServo(hardwareMap.servoController.get("servo_cnrtl"), 1);
        rollerServo = hardwareMap.servo.get("roller");
        components.add(winchServo);

        tank = new TankDrive(leftMotors, rightMotors);
        components.add(tank);
    }

    public void loop()
    {
        if (gamepad1.a)
        {
            tank.reverse();
        }


        tank.move(gamepad1.left_stick_y, gamepad1.right_stick_y);

        if (this.gamepad1.dpad_down && !this.gamepad1.dpad_up)
        {
            winchServo.increase();

        }
        else if (this.gamepad1.dpad_up && !this.gamepad1.dpad_down)
        {
            winchServo.decrease();

        }
        else
        {
        }
        if (this.gamepad1.dpad_left && !this.gamepad1.dpad_right)
        {
            if (gamepad1.x)
            {
                tank.move(0.4f, 0.4f);
            }
            winchMotor.setPower(-1);
            rollerServo.setPosition(1);
        }
        else if (this.gamepad1.dpad_right && !this.gamepad1.dpad_left)
        {
            if (gamepad1.x)
            {
                tank.move(-0.4f, -0.4f);
            }
            winchMotor.setPower(1);
            rollerServo.setPosition(1);
        }
        else
        {
            winchMotor.setPower(0);
            rollerServo.setPosition(.5);

        }
            for (Component component : components)
            {
                component.doit();
            }

            telemetry.addData("Winch Position", winchServo.location);
            telemetry.addData("Roller Pos", rollerServo.getPosition());
            telemetry.addData("Reverse", tank.isReverse());
        }
    }
