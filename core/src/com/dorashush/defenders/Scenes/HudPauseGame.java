package com.dorashush.defenders.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 02/25/18.
 */

public class HudPauseGame implements Disposable {
    public Stage stage;
    private Viewport viewPort;
    private Image tableBackground;

    private Image playBtn;
    private Image backToMenuBtn;



    public HudPauseGame(SpriteBatch sb){

        viewPort = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewPort,sb);

        tableBackground= new Image(new Texture("pausebackground.png"));
        tableBackground.setFillParent(true);


        playBtn = new Image(new Texture("pauseplaybutton.png"));
        backToMenuBtn = new Image(new Texture("pausebacktomenubutton.png"));



        Table table =new Table();
        table.center();
        table.setFillParent(true);
        table.add(playBtn);
        table.add(backToMenuBtn);


        stage.addActor(tableBackground);
        stage.addActor(table);
    }



    @Override
    public void dispose() {
        stage.dispose();
    }
}
