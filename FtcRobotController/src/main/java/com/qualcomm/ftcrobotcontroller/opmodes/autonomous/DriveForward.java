package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.TankDrive;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.timed_state;

/**
 * Created by winterst on 11/21/15.
 */
public class DriveForward extends StatefulAutonomous {

    @timed_state(duration = 1)
    public void driveForward()
    {
        tank.move(1,1);

    }
    @timed_state(duration = 1)
    public void driveBackward()
    {
        tank.move(-1,-1);
    }

}
