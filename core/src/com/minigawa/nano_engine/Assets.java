package com.minigawa.nano_engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by steve on 25/06/2015.
 */
public class Assets {
    public static TextureAtlas atlas;
    public static TextureRegion car;
    public static TextureRegion road;

    public static void load(){
        atlas= new TextureAtlas(Gdx.files.internal("images.atlas"));
        car= atlas.findRegion("car");
        road= atlas.findRegion("road");
    }

    public static void dispose(){
        atlas.dispose();
    }
}
