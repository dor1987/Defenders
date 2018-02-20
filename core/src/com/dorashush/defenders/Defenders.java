package com.dorashush.defenders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dorashush.defenders.Screens.EndGameScreen;
import com.dorashush.defenders.Screens.MenuScreen;
import com.dorashush.defenders.Screens.PlayScreen;

public class Defenders extends Game {
	public static SpriteBatch batch;

	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 800;

	public static final float PPM = 100;
	public static final int FIRST_LEVEL = 0;
	public static final int STARTING_SCORE = 0;
    public static final int STARTING_LIVES = 3;


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this,FIRST_LEVEL,STARTING_SCORE,STARTING_LIVES));

		//setScreen(new EndGameScreen(this,STARTING_SCORE));
		//setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
