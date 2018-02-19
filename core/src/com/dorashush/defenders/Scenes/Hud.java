package com.dorashush.defenders.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

/**
 * Created by Dor on 01/22/18.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewPort;
    private static Integer worldTimer;
    private float timeCount;
    private static Integer score; // Need to think of a better way, maybe pass hud to objects
    private static Integer levelNumber;
    private static Integer livesNumber;


    private Label countDownLabel;
    private Label worldLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private static Label levelLabel;
    private Label defenderLabel;

    private Label liveLabel;
    private static Label liveAmountLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        levelNumber = 1;
        livesNumber = 0;

        viewPort = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT,new OrthographicCamera());
        //viewPort = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),new OrthographicCamera());

        stage = new Stage(viewPort,sb);

        Table table =new Table();
        table.bottom();
        table.setFillParent(true);

        liveLabel = new Label("LIVES",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        liveAmountLabel = new Label(String.format("%03d",livesNumber),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        countDownLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel= new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel= new Label("TIME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel= new Label(String.format("%03d",levelNumber),new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // need to update it each level
        worldLabel = new Label("LEVEL",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        defenderLabel= new Label("SCORE",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(liveLabel).expandX().padBottom(5);
        table.add(defenderLabel).expandX().padBottom(5);
        table.add(worldLabel).expandX().padBottom(5);
        table.add(timeLabel).expandX().padBottom(5);
        table.row();
        table.add(liveAmountLabel).expandX().padBottom(25);
        table.add(scoreLabel).expandX().padBottom(25);
        table.add(levelLabel).expandX().padBottom(25);
        table.add(countDownLabel).expandX().padBottom(25);

        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount>=1) {
            worldTimer--;
            countDownLabel.setText(String.format("%03d",worldTimer));
            timeCount=0;
        }
    }

    public static void addLive(){
        livesNumber++;
        liveAmountLabel.setText(String.format("%03d",livesNumber));
    }
    public static void reduceLive(){
        Gdx.app.log("Lives Reduced","");

        livesNumber--;
        liveAmountLabel.setText(String.format("%03d",livesNumber));
    }

    public static int getLives(){
        return livesNumber;
    }

    public static void setLives(int lives){
        livesNumber+=lives;
        liveAmountLabel.setText(String.format("%03d",livesNumber));
    }

    public static void addScore(int value){
        score+=value;
        scoreLabel.setText(String.format("%06d",score));
    }
    public static int getScore(){
        return score;
    }
    public static int getTimeLeft(){
        return worldTimer;
    }
    public static void levelNumber(int levelNumber){
        levelLabel.setText(String.format("%03d",levelNumber));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
