package com.dorashush.defenders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dorashush.defenders.Screens.SplashScreen;

public class Defenders extends Game {
	public static SpriteBatch batch;

	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 800;

	public static final float PPM = 100;
	public static final int FIRST_LEVEL = 0;
	public static final int STARTING_SCORE = 0;
    public static final int STARTING_LIVES = 3;
	public static LeaderBoardHandler handler;

	//options setting
	public static float VOLUME;
	public static boolean FULL_CONTROL;
	public static boolean VIBRATION;
	public static boolean ISCHEATER = false;


	//sound
	public  AssetManager manager;

	//testing LIBGDX Preferences
	public static Preferences defendersSettings;




	public Defenders(LeaderBoardHandler handler){
		this.handler = handler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		soundLoad();


		//setScreen(new PlayScreen(this,FIRST_LEVEL,STARTING_SCORE,STARTING_LIVES));
		//setScreen(new EndGameScreen(this,STARTING_SCORE));
		//setScreen(new MainMenuScreen(this));
		setScreen(new SplashScreen(this,manager,batch));

		defendersSettings =Gdx.app.getPreferences("defendersSettings");
		VOLUME = defendersSettings.getFloat("volume",1f);
		FULL_CONTROL = defendersSettings.getBoolean("control",true);
		VIBRATION = defendersSettings.getBoolean("vibration",true);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose ()
	{
			manager.dispose();
			batch.dispose();
	}

	public void soundLoad(){
		manager.load("sound/bomb.mp3",Sound.class);
		manager.load("sound/braumishere.ogg",Sound.class);
		manager.load("sound/braumlivesanotherday.ogg",Sound.class);
		manager.load("sound/butuhaveme.ogg",Sound.class);
		manager.load("sound/keepursprithigh.ogg",Sound.class);
		manager.load("sound/motherdontlose.ogg",Sound.class);
		manager.load("sound/myshieldishereforu.ogg",Sound.class);
		manager.load("sound/myshieldismysword.ogg",Sound.class);
		manager.load("sound/notsofast.ogg",Sound.class);
		manager.load("sound/showmeyourbest.ogg",Sound.class);
		manager.load("sound/standbehindbraum.ogg",Sound.class);
		manager.load("sound/strikelikeram.ogg",Sound.class);
		manager.load("sound/takeheart.ogg",Sound.class);
		manager.load("sound/theheartisthestrongestmus.ogg",Sound.class);
		manager.load("sound/ucallbraum.ogg",Sound.class);
		manager.load("sound/ursafewithbraum.ogg",Sound.class);
		manager.load("sound/weareabouttobegin.ogg",Sound.class);
		manager.load("sound/hit1.ogg",Sound.class);
		manager.load("sound/hit2.ogg",Sound.class);
		manager.load("sound/hit3.ogg",Sound.class);
		manager.load("sound/shieldbash.ogg",Sound.class);
		manager.load("sound/bombtake.ogg",Sound.class);



		manager.finishLoading();
	}
}
