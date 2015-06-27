package com.minigawa.nano_engine;

import com.badlogic.gdx.math.Interpolation;

/**
 * Created by steve on 26/06/2015.
 */
public class Movement {
    public static enum Direction{
        UP,DOWN,LEFT,RIGHT
    }
    public Direction direction;
    public int distance;       // distance in points according the object's rangeOfMotion
    public Interpolation transition;

}
