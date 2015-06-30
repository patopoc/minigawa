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

    public Movement(){

    }

    public Movement(Direction direction, int distance, Interpolation transition){
        this.direction= direction;
        this.distance= distance;
        this.transition= transition;
    }

}
