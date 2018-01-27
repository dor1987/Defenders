package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;

/**
 * Created by Dor on 01/24/18.
 */

public class EndGameScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;

    public EndGameScreen(Game game,int score){
        this.game = game;
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,((Defenders)game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER",font);
        Label playAgain = new Label("Click to play again",font);
        Label scoreLabel = new Label("Your Score is:",font);
        Label scoreLabelInNumber= new Label(String.format("%06d",score),font);


        table.add(gameOverLabel).uniform();
        table.row();
        table.add(playAgain).padTop(10f).uniform();
        table.row();
        table.add(scoreLabel).uniform();
        table.add(scoreLabelInNumber).uniform();

        stage.addActor(table);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            game.setScreen(new PlayScreen((Defenders)game,0,0));
            dispose();
        }
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
