package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by fendleyc on 12/11/15.
 */
public class CClimbBlue extends StatefulAutonomous {
    @timed_state(duration = 4, next_state = "DriveToRamp", first = true)
    public void DrivePastRamp(){
        tank.move(-0.25f, -0.25f);
    }

    @timed_state(duration = 0.7, next_state = "SpinToRamp")
    public void DriveToRamp(){
        tank.move(0.65f, 0.66f);
    }

    @timed_state(duration = 2, next_state = "SetServoHigh")
    public void SpinToRamp(){
        tank.angleRotation(90);
    }

    @timed_state(duration = .5, next_state = "DeployWinch")
    public void SetServoHigh(){
        winchServo.setLocation(0.62);
    }

    @timed_state(duration = 8.8, next_state = "SetServoLow")
    public void DeployWinch(){
        winchMotor.setPower(1);
        tank.move(0.1f,0.1f);
    }

    @timed_state(duration = .5, next_state = "StartClimb")
    public void SetServoLow(){
        winchServo.setLocation(.3);
        winchMotor.setPower(0);
    }

    @timed_state(duration = 3, next_state = "FinishClimb")
    public void StartClimb(){
        winchMotor.setPower(-1);
    }

    @timed_state(duration = 6, next_state = "EndAuto")
    public void FinishClimb(){
        tank.move(0.4f, 0.4f);
        winchMotor.setPower(-1);
    }

    @timed_state(duration = 2.3)
    public void EndAuto(){
        winchMotor.setPower(0);
        tank.move(0,0);
    }

}
