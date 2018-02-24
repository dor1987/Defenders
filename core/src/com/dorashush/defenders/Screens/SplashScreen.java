package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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
    private ExtendViewport backViewPort;
    private Table table;
    private Image logo;


    public SplashScreen(final Defenders game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, camera);

        stage = new Stage(viewport,game.batch);
        Gdx.input.setInputProcessor(stage);

        Texture logoText = new Texture("splashscreenlogo.png");
        logo = new Image(logoText);

        logo.setOrigin(logo.getWidth()/2,logo.getHeight()/2);
        logo.setSize(322f,172f);
        stage.addActor(logo);







    }

    @Override
    public void show() {
        logo.setPosition(stage.getWidth()/2,stage.getHeight()/2+logo.getHeight());

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen((Defenders) game));
            }
        };


        logo.addAction(sequence(alpha(0),scaleTo(.1f,.1f),parallel(fadeIn(2f, Interpolation.pow2),scaleTo(2f,2f,2.5f,Interpolation.pow5),
                moveTo(Defenders.V_WIDTH/2-logo.getWidth()/Defenders.PPM,Defenders.V_HEIGHT/2-logo.getHeight()/Defenders.PPM,2f,Interpolation.swing)),delay(1.5f),fadeOut(1.25f),run(transitionRunnable)));

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
