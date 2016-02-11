package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by fendleyc on 12/12/15.
 */
public class LowZoneBlue extends StatefulAutonomous {

    @timed_state(duration = 5.05, next_state = "DriveToRamp", first = true)
    public void DrivePastRamp(){
        tank.move(-0.25f, -0.25f);
    }

    @timed_state(duration = 0.7, next_state = "SpinToRamp")
    public void DriveToRamp(){
        tank.move(0.65f, 0.66f);
    }

    @timed_state(duration = 2, next_state = "DriveUp")
    public void SpinToRamp(){
        tank.angleRotation(90);
    }

    @timed_state(duration = 6)
    public void DriveUp(){
        tank.move(1,1);
    }
}
