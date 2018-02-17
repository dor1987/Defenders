package com.dorashush.defenders.Scenes;

import com.badlogic.gdx.Gdx;
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
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 01/27/18.
 */



public class HudForEndLevel implements Disposable {
    public Stage stage;
    private Viewport viewPort;


    static Label youWinOrLooseLabel;
     Label nextLevelLabel;
     Label scoreLabel;
    static  Label scoreLabelInNumber;
     Label timLeftBonusLabel;
    static Label timLeftBonusLabelInNumber;
     Label totalScoreLabel;
    static Label totalScoreLabelInNumber;
     Label.LabelStyle font;

    public HudForEndLevel(SpriteBatch sb){

        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
      viewPort = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT,new OrthographicCamera());
       // viewPort = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),new OrthographicCamera());

        stage = new Stage(viewPort,sb);

        Table table =new Table();
        table.center();
        table.setFillParent(true);

         youWinOrLooseLabel = new Label("YOU WIN",font);
         nextLevelLabel = new Label("Click on screen to continue",font);
         scoreLabel = new Label("Your Score:",font);
         scoreLabelInNumber= new Label(String.format("%06d",Hud.getScore()),font);
         timLeftBonusLabel= new Label("Time left Bonus:",font);
         timLeftBonusLabelInNumber = new Label(String.format("%06d",Hud.getTimeLeft()),font);
         totalScoreLabel= new Label("Total Score:",font);
         totalScoreLabelInNumber= new Label(String.format("%06d",Hud.getTimeLeft()+Hud.getScore()),font);

        table.add(youWinOrLooseLabel);
        table.row();
        table.add(scoreLabel);
        table.add(scoreLabelInNumber);
        table.row();
        table.add(timLeftBonusLabel);
        table.add(timLeftBonusLabelInNumber);
        table.row();
        table.add(totalScoreLabel);
        table.add(totalScoreLabelInNumber);
        table.row();
        table.add(nextLevelLabel);

        stage.addActor(table);
    }

    public static void setGameStatus(PlayScreen.GameStatus gameStatus){
      if(gameStatus== PlayScreen.GameStatus.LOOSE)
          youWinOrLooseLabel.setText("YOU LOOSE");
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




    @Override
    public void dispose() {
        stage.dispose();
    }
}
