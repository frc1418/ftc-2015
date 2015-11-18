package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import java.util.ArrayList;
import java.util.List;

public class TankOpMode extends OpMode{
    DcMotor[] rightMotors = new DcMotor[2];
    DcMotor[] leftMotors = new DcMotor[2];

    DcMotor winchMotor;

    TankDrive tank;

    List<Component> components = new ArrayList<Component>();

    public void init()
    {
    	leftMotors[0] = hardwareMap.dcMotor.get("motor_1");
    	leftMotors[1] = hardwareMap.dcMotor.get("motor_2");
        rightMotors[0] = hardwareMap.dcMotor.get("motor_3");
        rightMotors[1] = hardwareMap.dcMotor.get("motor_4");


    	leftMotors[0].setDirection(DcMotor.Direction.REVERSE);
        leftMotors[1].setDirection(DcMotor.Direction.REVERSE);

        winchMotor = hardwareMap.dcMotor.get("winch_motor");

        tank = new TankDrive(leftMotors, rightMotors);
        components.add(tank);
    }

    public void loop()
    {
        if(this.gamepad1.a){
            tank.reverse();
        }
        if(this.gamepad1.dpad_down && !this.gamepad1.dpad_up){
            winchMotor.setPower(-1);
        }else if(this.gamepad1.dpad_up && !this.gamepad1.dpad_down){
            winchMotor.setPower(1);
        }else{
            winchMotor.setPower(0);
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
