package com.minigawa.nano_engine;

/**
 * Created by steve on 26/06/2015.
 */
public class GridPosition {
    public int x;
    public int y;
    public boolean isCheckPoint;
    public GridPosition(){

    }

    public GridPosition(int x, int y, boolean isCheckPoint){
        this.x=x;
        this.y=y;
        this.isCheckPoint= isCheckPoint;
    }
}
