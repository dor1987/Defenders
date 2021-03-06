package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Dor on 02/24/18.
 */

public class SplashScreen implements Screen {
    private final Game game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Image logo;
    private AssetManager manager;

    public SplashScreen(final Defenders game , AssetManager manager, SpriteBatch sb) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, camera);
        this.manager=manager;

        stage = new Stage(viewport,sb);
        Gdx.input.setInputProcessor(stage);
        Texture logoText = new Texture("splashscreenlogo.png");
        logo = new Image(logoText);
        logo.setOrigin(logo.getWidth()/2,logo.getHeight()/2);
        stage.addActor(logo);

    }

    @Override
    public void show() {// use to set starting position
        logo.setPosition(stage.getWidth()/2,stage.getHeight()/2+logo.getHeight());

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen((Defenders)game,manager));

            }
        };


        logo.addAction(sequence(alpha(0),scaleTo(.1f,.1f),parallel(fadeIn(2f, Interpolation.pow2),scaleTo(1.2f,1.2f,2.5f,Interpolation.pow5),
                moveTo(Defenders.V_WIDTH/2/Defenders.PPM+logo.getWidth()/4,Defenders.V_HEIGHT/2-logo.getHeight()/3,2f,Interpolation.swing)),delay(1.5f),fadeOut(1.25f),run(transitionRunnable)));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();


    }



    public void update(float dt){
    stage.act(dt);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
