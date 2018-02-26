package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;

/**
 * Created by Dor on 02/23/18.
 */

public class MainMenuScreen implements Screen {
    private Game game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage,backStage;
    private ExtendViewport backViewPort;
    private Image backgroundTexture;
    private Table table;
    private Image playBtn;
    private Image optionsBtn;
    private Image leaderBoardBtn;
    private Image menuTitle;


    public MainMenuScreen(final Defenders game) {
        this.game = game;

        camera = new OrthographicCamera();
       // camera.update();

        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, camera);
        backViewPort = new ExtendViewport( Defenders.V_WIDTH, Defenders.V_HEIGHT );
        backgroundTexture = new Image(new Texture("background.png"));
        backgroundTexture.setFillParent(true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport.apply();
        backViewPort.apply()
        ;
        stage = new Stage(viewport, game.batch);
        backStage = new Stage(backViewPort);

        table =new Table();
        playBtn = new Image(new Texture("newgamebtn.png"));
        optionsBtn = new Image(new Texture("optionsbtn.png"));
        leaderBoardBtn = new Image(new Texture("leaderboardbtn.png"));
        menuTitle = new Image(new Texture("menutitle.png"));




        table.center();
        table.setFillParent(true);

        table.add(menuTitle).padBottom(70f);
        table.row();
        table.add(playBtn).padBottom(20f);
        table.row();
        table.add(optionsBtn).padBottom(20f);
        table.row();
        table.add(leaderBoardBtn).padBottom(20f);


        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("","BEEN CLICKED!");
                game.setScreen(new PlayScreen(game,Defenders.FIRST_LEVEL,Defenders.STARTING_SCORE,Defenders.STARTING_LIVES));
                dispose();
            }
        });

        optionsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add option Screen
                game.setScreen(new OptionScreen(game));

                dispose();
            }
        });

        leaderBoardBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                game.setScreen(new LeaderBoardScreen(game));
                dispose();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        backStage.addActor(backgroundTexture);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    backStage.act();
    backStage.draw();
    stage.act();
    stage.draw();

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
    backStage.dispose();
    stage.dispose();
    }
}
