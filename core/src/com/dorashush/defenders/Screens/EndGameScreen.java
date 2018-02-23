package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;

import sun.rmi.runtime.Log;

/**
 * Created by Dor on 01/24/18.
 */

public class EndGameScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Label.LabelStyle labelStyle;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextField.TextFieldStyle textFieldStyle;
    private Table table;
    private TextArea userNameTextArea;
    private TextButton getUserNameBtn;
    private TextButton backToMainMenuBtn;
    private TextButton restartGameBtn;


    private boolean isHighScore;

    public EndGameScreen(final Game game, final int score) {
        this.game = game;
        viewport = new FitViewport(Defenders.V_WIDTH, Defenders.V_HEIGHT, new OrthographicCamera());
        //viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), new OrthographicCamera());

        stage = new Stage(viewport, ((Defenders) game).batch);

        Gdx.input.setInputProcessor(stage);
        labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = new BitmapFont();
        textFieldStyle.fontColor = Color.GRAY;

        isHighScore = false;
        table = new Table();
        table.center();
        table.setFillParent(true);
        //uiSkin = Gdx.files.internal("uiskin.json")

        Label gameOverLabel = new Label("GAME OVER", labelStyle);
        Label playAgain = new Label("Click to play again", labelStyle);
        Label scoreLabel = new Label("Your Score is:", labelStyle);
        Label scoreLabelInNumber = new Label(String.format("%06d", score), labelStyle);

        getUserNameBtn = new TextButton("Submit", textButtonStyle);
        userNameTextArea = new TextArea("Enter Your Name", textFieldStyle);
        userNameTextArea.setMaxLength(10);
        backToMainMenuBtn = new TextButton("Main Menu", textButtonStyle);
        restartGameBtn = new TextButton("Replay Game", textButtonStyle);


        table.add(gameOverLabel).uniform();
        table.row();
        table.add(playAgain).padTop(10f).uniform();
        table.row();
        table.add(scoreLabel).uniform();
        table.add(scoreLabelInNumber).uniform().padBottom(10f);
        table.row();

        table.add(getUserNameBtn).padBottom(100f);
        table.add(userNameTextArea).padBottom(100f);

        table.row();
        table.add(backToMainMenuBtn).padRight(30f);
        table.add(restartGameBtn);


        stage.addActor(table);


        getUserNameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Defenders.handler.addPlayerScoreToDataBase(userNameTextArea.getText(), score);

                table.removeActor(getUserNameBtn);
                table.removeActor(userNameTextArea);

                //  game.setScreen(new PlayScreen((Defenders)game,0,0,((Defenders) game).STARTING_LIVES));

                // dispose();
            }
        });


        userNameTextArea.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userNameTextArea.setText("");
            }
        });

        backToMainMenuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MenuScreen((Defenders)game));
                dispose();
            }
        });

        restartGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new PlayScreen((Defenders) game,Defenders.FIRST_LEVEL,Defenders.STARTING_SCORE,Defenders.STARTING_LIVES));
                dispose();
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        /*
        if(Gdx.input.justTouched()){
            game.setScreen(new PlayScreen((Defenders)game,0,0,((Defenders) game).STARTING_LIVES));
            dispose();
        }
        */
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
    stage.dispose();
    }
}
