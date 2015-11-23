package com.qualcomm.ftcrobotcontroller.opmodes.components;

/**
 * Created by winterst on 11/22/15.
 */
public class DebounceButton
{
    boolean button;
    long latest = 0;
    long time;

    public DebounceButton(boolean button)
    {
        this.button = button;
    }
    public boolean get()
    {
        time = System.currentTimeMillis();
        if (button)
        {
            if(time - latest > 600)
            {
                latest = time;
                return true;
            }
        }
        return false;
    }

}
