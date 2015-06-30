package com.minigawa.nano_engine;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.minigawa.GameConsole;

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
    private String currState;
    private Array<Weapon> weapons;
    private Array<State> states;
    private float transitionTime;
    private float transitionWaitTime;
    private Array<GridPosition> rangeOfMotion; //available grid points for object to move
    private Movement defaultMotion;            //for automatic object motion (eg. enemies)
    private float speed;                       //posibly points per second
    private boolean isMoving=false;

    public GameObject(ObjectType type, GridPosition initialPosition, int strength, boolean isBreakable,
                      String initialState, Array<Weapon> weapons, Array<State> states,
                      Array<GridPosition> rangeOfMotion, Movement defaultMotion, float speed){
        this.type= type;
        this.currPosition= initialPosition;
        this.strength= strength;
        this.isBreakable= isBreakable;
        this.weapons= weapons;
        this.states= states;
        this.rangeOfMotion= rangeOfMotion;
        this.defaultMotion= defaultMotion;
        this.speed= speed;

        setState(initialState);

        RealPosition realPosition= calculateRealMotion(null);

        setPosition(realPosition.x, realPosition.y);
        setColor(Color.YELLOW);
    }

    public void setState(String state){
        if(states != null) {
            for (State s : states) {
                if (s.state.equals(state)) {
                    currState = s.state;
                    break;
                }
            }
        }
    }
    public void moveObject(Movement movement){
        if(!isMoving) {
            RealPosition realPosition = calculateRealMotion(movement);
            isMoving=true;
            addAction(sequence(moveTo(realPosition.x, realPosition.y, speed, movement.transition), run(new Runnable() {
                @Override
                public void run() {
                    isMoving=false;
                    checkPoint();
                }
            })));
        }
    }

    public void traversePath(Array<Movement> path){
        SequenceAction sequenceAction= new SequenceAction();
        RealPosition realPosition;
        for(Movement m : path) {
            realPosition = calculateRealMotion(m);
            sequenceAction.addAction(moveTo(realPosition.x, realPosition.y, speed, m.transition));
        }
        addAction(sequenceAction);
    }

    private RealPosition calculateRealMotion(Movement movement){
        RealPosition realPosition= new RealPosition(getX(), getY());
        GridPosition nearestPoint=null;

        if(movement != null) {
            if (movement.direction == Movement.Direction.UP) {
                for (GridPosition position : rangeOfMotion) {
                    if (position.y > currPosition.y) {
                        if (nearestPoint == null) {
                            nearestPoint = new GridPosition();
                            nearestPoint.x = position.x;
                            nearestPoint.y = position.y;
                        } else if (pointsDistance(currPosition, position) <= pointsDistance(currPosition, nearestPoint)){
                                //&& Math.pow((position.x - currPosition.x), 2) < Math.pow((nearestPoint.x - currPosition.x), 2)) { //Consider de closest X to currPosition
                            nearestPoint = position;
                        }

                    }
                }
            } else if (movement.direction == Movement.Direction.DOWN) {
                for (GridPosition position : rangeOfMotion) {
                    if (position.y < currPosition.y) {
                        if (nearestPoint == null) {
                            nearestPoint = new GridPosition();
                            nearestPoint.x = position.x;
                            nearestPoint.y = position.y;
                        } else if (pointsDistance(currPosition, position) <= pointsDistance(currPosition, nearestPoint)){
                                //&& Math.pow((position.y - currPosition.y), 2) < Math.pow((nearestPoint.y - currPosition.y), 2)) { //Consider de closest X to currPosition
                            nearestPoint = position;
                        }

                    }
                }
            } else if (movement.direction == Movement.Direction.LEFT) {
                for (GridPosition position : rangeOfMotion) {
                    if (position.x < currPosition.x) {
                        if (nearestPoint == null) {
                            nearestPoint = new GridPosition();
                            nearestPoint.x = position.x;
                            nearestPoint.y = position.y;
                        } else if (pointsDistance(currPosition, position) <= pointsDistance(currPosition, nearestPoint)){
                                //&& Math.pow((position.y - currPosition.y), 2) < Math.pow((nearestPoint.y - currPosition.y), 2)) { //Consider de closest X to currPosition

                            nearestPoint = position;
                        }

                    }
                }
            } else if (movement.direction == Movement.Direction.RIGHT) {
                for (GridPosition position : rangeOfMotion) {
                    if (position.x > currPosition.x) {
                        if (nearestPoint == null) {
                            nearestPoint = new GridPosition();
                            nearestPoint.x = position.x;
                            nearestPoint.y = position.y;
                        } else if (pointsDistance(currPosition, position) <= pointsDistance(currPosition, nearestPoint)){
                                //&& Math.pow((position.x - currPosition.x), 2) < Math.pow((nearestPoint.x - currPosition.x), 2)) { //Consider de closest X to currPosition
                            nearestPoint = position;
                        }

                    }
                }
            }

            currPosition = nearestPoint;
        }

        realPosition.x= (currPosition.x * GameConsole.GRID_CELL_WIDTH) - GameConsole.GRID_CELL_WIDTH/2;
        realPosition.y= (currPosition.y * GameConsole.GRID_CELL_HEIGHT) - GameConsole.GRID_CELL_HEIGHT/2;

        return realPosition;
    }

    private double pointsDistance(GridPosition point1, GridPosition point2){
        //Calculate Hypotenuse between the 2 points
        double distance=0;
        int catAd= point2.x - point1.x;
        int catOp= point2.y - point1.y;
        distance= Math.pow(catAd,2) + Math.pow(catOp,2);

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

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        batch.draw(Assets.car, getX(), getY(), Assets.car.getRegionWidth()/2, Assets.car.getRegionHeight()/2,
                Assets.car.getRegionWidth(), Assets.car.getRegionHeight(),1,1,getRotation());
    }
}
