package com.dorashush.defenders.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 02/25/18.
 */

public class HudPauseGame{
    public Stage stage;
    private Viewport viewPort;
    private Image tableBackground;
    OrthographicCamera cam;
    boolean backToGamePressed, backToMenuPressed;

  //  private Image playBtn;
    private Image backToMenuBtn;



    public HudPauseGame (){
        cam = new OrthographicCamera();

        //viewPort = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT,new OrthographicCamera());
        viewPort = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, cam);

        stage = new Stage(viewPort, Defenders.batch);

        tableBackground= new Image(new Texture("pausebackground.png"));
       // tableBackground.setFillParent(true);
        tableBackground.setPosition(Defenders.V_WIDTH/2-tableBackground.getWidth()/2,Defenders.V_HEIGHT/2-tableBackground.getHeight()/3);

        //playBtn = new Image(new Texture("pauseplaybutton.png"));
        backToMenuBtn = new Image(new Texture("pausebacktomenubutton.png"));




        Gdx.input.setInputProcessor(stage);


        Table table =new Table();
        table.center();
        table.setFillParent(true);




        Image  playBtn = new Image(new Texture("pauseplaybutton.png"));

        playBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backToGamePressed = true;
                Gdx.app.log("","BACK TO GAME CLICKED");
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                backToGamePressed = false;
            }
        });




/*
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                backToGamePressed=true;
                Gdx.app.log("","BACK TO GAME CLICKED");

            }
        });
*/
/*
        backToMenuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                backToMenuPressed=true;
            }
        });
*/

        table.add(playBtn).pad(40,40,40,40);
        table.add(backToMenuBtn).pad(40,40,40,40);


        stage.addActor(tableBackground);
        stage.addActor(table);

    }

//TODO add buttons functions


    public boolean isBackToGamePressed() {
        return backToGamePressed;
    }

    public boolean isBackToMenuPressed() {
        return backToMenuPressed;
    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewPort.update(width, height);
    }


}
