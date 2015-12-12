package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by fendleyc on 12/12/15.
 */
public class LowZoneBlue extends StatefulAutonomous {
    @timed_state(duration = 5.25)
    public void aDriveToRamp(){
        tank.move(-0.25f, -0.25f);
    }

    @timed_state(duration = 0.5)
    public void bDriveToRamp(){
        tank.move(0.75f, 0.75f);
    }

    @timed_state(duration = 1.15)
    public void cSpinToRamp(){
        tank.move(-1, 1);
    }

    @timed_state(duration = 6)
    public void dDriveUp(){
        tank.move(1,1);
    }
}
