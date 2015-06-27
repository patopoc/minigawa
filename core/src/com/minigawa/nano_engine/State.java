package com.minigawa.nano_engine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * Created by steve on 26/06/2015.
 */
public class State {
    public Animation animation;
    public float duration;
    public boolean loop;
    public String state;
    public SequenceAction actionSequence;

    public State(){
        //Build SequenceAction
    }
}
