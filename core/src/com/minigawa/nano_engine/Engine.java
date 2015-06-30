package com.minigawa.nano_engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.minigawa.GameConsole;

/**
 * Created by steve on 27/06/2015.
 */
public class Engine extends Group {

    GameObject gameObject;

    public Engine(Controller controller){
        Array<GridPosition> rangeOfMotion= new Array<GridPosition>();
        rangeOfMotion.add(new GridPosition(2,4,true));
        rangeOfMotion.add(new GridPosition(4,2,true));
        rangeOfMotion.add(new GridPosition(7,3,true));
        rangeOfMotion.add(new GridPosition(9,2,true));
        gameObject= new GameObject(null,rangeOfMotion.first(), 1,false,"iddle",null,null,
                rangeOfMotion,null,0.5f);
        addActor(gameObject);

    }

    public void testMovement(){
        Array<Movement> movements= new Array<Movement>();
        movements.add(new Movement(Movement.Direction.RIGHT,1, Interpolation.linear));
        movements.add(new Movement(Movement.Direction.RIGHT,1, Interpolation.linear));
        movements.add(new Movement(Movement.Direction.LEFT,1, Interpolation.linear));
        movements.add(new Movement(Movement.Direction.RIGHT,1, Interpolation.linear));
        movements.add(new Movement(Movement.Direction.RIGHT,1, Interpolation.linear));

        gameObject.traversePath(movements);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch,parentAlpha);
    }

}
