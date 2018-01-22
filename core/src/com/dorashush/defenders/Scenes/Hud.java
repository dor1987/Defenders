package com.dorashush.defenders.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;

/**
 * Created by Dor on 01/22/18.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewPort;
    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countDownLabel;
    Label worldLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label defenderLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewPort = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewPort,sb);

        Table table =new Table();
        table.bottom();
        table.setFillParent(true);

        countDownLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel= new Label(String.format("%06d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel= new Label("TIME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel= new Label("1",new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // need to update it each level
        worldLabel = new Label("WORLD",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        defenderLabel= new Label("SCORE",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(defenderLabel).expandX().padBottom(5);
        table.add(worldLabel).expandX().padBottom(5);
        table.add(timeLabel).expandX().padBottom(5);
        table.row();
        table.add(scoreLabel).expandX().padBottom(25);
        table.add(levelLabel).expandX().padBottom(25);
        table.add(countDownLabel).expandX().padBottom(25);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
