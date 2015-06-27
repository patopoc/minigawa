package com.minigawa.nano_engine;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

/**
 * Created by steve on 26/06/2015.
 */
public class GameObject extends Group {
    public static enum ObjectType{
        PLAYER, NIO, ENEMY, TARGET, BULLET, WEAPON, OBSTACLE
    }
    private ObjectType type;
    private GridPosition currPosition;
    private int strength;                       // each strength value has a State
    private boolean isBreakable= true;          // Obstacles may be unbreakable
    private int currState;
    private Array<Weapon> weapons;
    private Array<State> states;
    private float transitionTime;
    private float transitionWaitTime;
    private Array<GridPosition> rangeOfMotion; //available grid points for object to move
    private Movement defaultMotion;            //for automatic object motion (eg. enemies)
    private float speed;                       //posibly points per second

    public GameObject(){

    }

    public void Move(Movement movement, int consoleWidth, int consoleHeight, int gridWidth, int gridHeight){
        RealPosition realPosition= calculateRealMotion(movement,consoleWidth,consoleHeight,gridWidth,gridHeight);
        addAction(sequence(moveTo(realPosition.x,realPosition.y,speed, movement.transition),run(new Runnable() {
            @Override
            public void run() {
                checkPoint();
            }
        })));
    }

    private RealPosition calculateRealMotion(Movement movement, int consoleWidth, int consoleHeight, int gridWidth, int gridHeight){
        RealPosition realPosition= new RealPosition(getX(), getY());
        GridPosition nearestPoint=null;

        if(movement.direction == Movement.Direction.UP){
            for(GridPosition position : rangeOfMotion){
                if(position.y > currPosition.y){
                    if(nearestPoint == null){
                        nearestPoint= new GridPosition();
                        nearestPoint.x= position.x;
                        nearestPoint.y= position.y;
                    }
                    else if(pointsDistance(currPosition, position) <= pointsDistance(currPosition, nearestPoint)
                            && ((position.x - currPosition.x)^2) < ((nearestPoint.x - currPosition.x)^2)){ //Consider de closest X to currPosition
                        nearestPoint= position;
                    }

                }
            }
            currPosition= nearestPoint;
        }
        else if(movement.direction == Movement.Direction.DOWN){
            for(GridPosition position : rangeOfMotion){
                if(position.y < currPosition.y){
                    if(nearestPoint == null){
                        nearestPoint= new GridPosition();
                        nearestPoint.x= position.x;
                        nearestPoint.y= position.y;
                    }
                    else if(pointsDistance(currPosition, position) <= pointsDistance(currPosition, nearestPoint)
                            && ((position.y - currPosition.y)^2) < ((nearestPoint.y - currPosition.y)^2)){ //Consider de closest X to currPosition
                        nearestPoint= position;
                    }

                }
            }
            currPosition= nearestPoint;
        }
        else if(movement.direction == Movement.Direction.LEFT){
            for(GridPosition position : rangeOfMotion){
                if(position.x < currPosition.x){
                    if(nearestPoint == null){
                        nearestPoint= new GridPosition();
                        nearestPoint.x= position.x;
                        nearestPoint.y= position.y;
                    }
                    else if(pointsDistance(currPosition, position) <= pointsDistance(currPosition, nearestPoint)
                            && ((position.y - currPosition.y)^2) < ((nearestPoint.y - currPosition.y)^2)){ //Consider de closest X to currPosition
                        nearestPoint= position;
                    }

                }
            }
            currPosition= nearestPoint;
        }
        else if(movement.direction == Movement.Direction.RIGHT){
            for(GridPosition position : rangeOfMotion){
                if(position.x > currPosition.x){
                    if(nearestPoint == null){
                        nearestPoint= new GridPosition();
                        nearestPoint.x= position.x;
                        nearestPoint.y= position.y;
                    }
                    else if(pointsDistance(currPosition, position) <= pointsDistance(currPosition, nearestPoint)
                            && ((position.x - currPosition.x)^2) < ((nearestPoint.x - currPosition.x)^2)){ //Consider de closest X to currPosition
                        nearestPoint= position;
                    }

                }
            }
            currPosition= nearestPoint;
        }


        realPosition.x= (currPosition.x * gridWidth) - gridWidth/2;
        realPosition.y= (currPosition.y * gridHeight) - gridHeight/2;

        return realPosition;
    }

    private int pointsDistance(GridPosition point1, GridPosition point2){
        //Calculate Hypotenuse between the 2 points
        int distance=0;
        int catAd= point2.x - point1.x;
        int catOp= point2.y - point1.y;
        distance= catAd ^ 2 + catOp ^ 2;

        return distance;
    }

    public void checkPoint(){

    }

    private class RealPosition{
        public float x;
        public float y;

        public RealPosition(float x, float y){
            this.x= x;
            this.y=y;
        }
    }
}
