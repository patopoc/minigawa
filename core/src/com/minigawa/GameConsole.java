package com.minigawa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minigawa.nano_engine.Assets;
import com.minigawa.screens.GameScreen;

public class GameConsole extends Game {
	SpriteBatch batch;
	Texture img;
    public final static int WIDTH=800;
    public final static int HEIGHT=480;
    public final static int GRID_WIDTH=10;
    public final static int GRID_HEIGHT=6;
    public final static int GRID_CELL_WIDTH= WIDTH/GRID_WIDTH;
    public final static int GRID_CELL_HEIGHT= HEIGHT/GRID_HEIGHT;

    private GameScreen gameScreen;
	
	@Override
	public void create () {
		Assets.load();
        gameScreen= new GameScreen();
        setScreen(gameScreen);
	}

	@Override
	public void dispose () {
		Assets.dispose();
        gameScreen.dispose();
	}
}
