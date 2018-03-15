package com.dorashush.defenders.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 01/27/18.
 */



public class HudForEndLevel implements Disposable {
    public Stage stage;
    private Viewport viewPort;
    private Skin skin;
    static Label youWinOrLooseLabel,scoreLabelInNumber,timLeftBonusLabelInNumber,totalScoreLabelInNumber;
    private Label nextLevelLabel,scoreLabel,timLeftBonusLabel,totalScoreLabel;
    private Image endGamePanelBackground,amountOfStarToShow;
    private Texture noStar,oneStar,twoStar,threeStar;
    private Table table,table2,table3;
    AssetManager manager;

    public HudForEndLevel(SpriteBatch sb, AssetManager manager){
        this.manager = manager;
        viewPort = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewPort,sb);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
       initBackGround();
       initStars();
       initLabels();
       initTables();
       stageInit();



    }

    public void initTables(){
        table =new Table();
        table.center().top().padTop(150);
        table.setFillParent(true);

        table2 =new Table();
        table2.padTop(10);
        table2.setPosition(table.getX()/2,table.getY()-table.getHeight());
        table2.setFillParent(true);
        table2.defaults().width(50).expandX();

        table3 =new Table();
        table3.padTop(180);
        table3.setPosition(table2.getX()/2,table2.getY()-table2.getHeight());
        table3.setFillParent(true);

        table.add(youWinOrLooseLabel).setActorWidth(480);
        table.row().padTop(20);
        table.add(amountOfStarToShow).setActorWidth(480);


        table2.add(scoreLabel);
        table2.add(scoreLabelInNumber);
        table2.row();
        table2.add(timLeftBonusLabel);
        table2.add(timLeftBonusLabelInNumber);
        table2.row();
        table2.add(totalScoreLabel);
        table2.add(totalScoreLabelInNumber);
        table2.row();

        table3.add(nextLevelLabel);
    }
    public void initBackGround(){
        endGamePanelBackground= new Image(new Texture("endlevelpanel.png"));
        endGamePanelBackground.setPosition(Defenders.V_WIDTH/2-endGamePanelBackground.getWidth()/2,Defenders.V_HEIGHT/2-endGamePanelBackground.getHeight()/3);
    }
    public void initStars(){
        noStar = new Texture("nostars.png");
        oneStar =new Texture("onestar.png");
        twoStar = new Texture("twostar.png");
        threeStar = new Texture("threestars.png");
        amountOfStarToShow = new Image(noStar);
    }
    public void initLabels(){
        youWinOrLooseLabel = new Label("YOU WIN",skin);
        youWinOrLooseLabel.setFontScale(1.5f);
        nextLevelLabel = new Label("Click on screen to continue",skin);
        nextLevelLabel.setFontScale(1.2f);
        scoreLabel = new Label("Your Score:",skin);
        scoreLabel.setFontScale(1.2f);
        scoreLabelInNumber= new Label(String.format("%06d",Hud.getScore()),skin);
        scoreLabelInNumber.setFontScale(1.2f);
        timLeftBonusLabel= new Label("Time left Bonus:",skin);
        timLeftBonusLabel.setFontScale(1.2f);
        timLeftBonusLabelInNumber = new Label(String.format("%06d",Hud.getTimeLeft()),skin);

        timLeftBonusLabelInNumber.setFontScale(1.2f);
        totalScoreLabel= new Label("Total Score:",skin);
        totalScoreLabel.setFontScale(1.2f);
        totalScoreLabelInNumber= new Label(String.format("%06d",Hud.getTimeLeft()+Hud.getScore()),skin);
        totalScoreLabelInNumber.setFontScale(1.2f);
    }
    public void stageInit(){
        stage.addActor(endGamePanelBackground);
        stage.addActor(table);
        stage.addActor(table2);
        stage.addActor(table3);
    }


    public static void setGameStatus(PlayScreen.GameStatus gameStatus){
      if(gameStatus== PlayScreen.GameStatus.LOOSE) {
          youWinOrLooseLabel.setText("YOU LOSE");
      }
      else if(gameStatus== PlayScreen.GameStatus.WIN)
          youWinOrLooseLabel.setText("You WIN");
    }
    public static void setScore(int value){
        scoreLabelInNumber.setText(String.format("%06d",value));
    }
    public static void setTimeLeftBonus(int value){
        timLeftBonusLabelInNumber.setText(String.format("%06d",value));
    }
    public static void setTotalScore(int value){
        totalScoreLabelInNumber.setText(String.format("%06d",value));
    }
    public void setAmountOfStars(int timeLeft,PlayScreen.GameStatus gameStatus) {
        if (gameStatus== PlayScreen.GameStatus.LOOSE) {
            amountOfStarToShow.setDrawable(new SpriteDrawable(new Sprite(noStar)));
        }

        else {
            if (timeLeft >= 200) {
                amountOfStarToShow.setDrawable(new SpriteDrawable(new Sprite(threeStar)));
            } else if (timeLeft >= 100) {
                amountOfStarToShow.setDrawable(new SpriteDrawable(new Sprite(twoStar)));
            } else if (timeLeft >= 0) {
                amountOfStarToShow.setDrawable(new SpriteDrawable(new Sprite(oneStar)));
            } else {
                amountOfStarToShow.setDrawable(new SpriteDrawable(new Sprite(noStar)));
            }
        }

    }

        @Override
    public void dispose() {
        stage.dispose();
    }
}
