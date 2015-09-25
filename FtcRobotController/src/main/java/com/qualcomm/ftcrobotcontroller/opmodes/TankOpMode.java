package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import java.util.ArrayList;
import java.util.List;

public class TankOpMode extends OpMode{
    DcMotor[] rightMotors = new DcMotor[1];
    DcMotor[] leftMotors = new DcMotor[1];

    TankDrive tank;

    List<Component> components = new ArrayList<Component>();

    public void init()
    {
    	leftMotors[0] = hardwareMap.dcMotor.get("motor_2");
    	rightMotors[0] = hardwareMap.dcMotor.get("motor_1");

    	leftMotors[0].setDirection(DcMotor.Direction.REVERSE);

        tank = new TankDrive(leftMotors, rightMotors);
        components.add(tank);
    }

    public void loop()
    {
        if(this.gamepad1.a){
            tank.reverse();
        }
        if(this.gamepad1.dpad_down && !this.gamepad1.dpad_up){
            float speed = tank.getSpeedVariable();

            speed = Math.max(0.5f, speed-0.1f);

            tank.setSpeedVariable(speed);
        }

        if(this.gamepad1.dpad_up && !this.gamepad1.dpad_down){
            float speed = tank.getSpeedVariable();

            speed = Math.min(1f, speed+0.1f);

            tank.setSpeedVariable(speed);
        }

        System.out.println("Speed: " + tank.getSpeedVariable());
        telemetry.addData("Speed", tank.getSpeedVariable());

        tank.move(gamepad1.left_stick_y, gamepad1.right_stick_y);


        for(int i=0; i< components.size(); i++){
            System.out.println();
            components.get(i).doit();
        }
    }
}
