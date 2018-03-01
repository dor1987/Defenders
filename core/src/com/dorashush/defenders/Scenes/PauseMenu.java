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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 03/01/18.
 */

public class PauseMenu {
    Viewport viewport;
    Stage stage;
    boolean  playPressed, menuPressed;
    OrthographicCamera cam;


    public PauseMenu(){
        cam = new OrthographicCamera();
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, cam);
        stage = new Stage(viewport, Defenders.batch);

       // Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Image tableBackground = new Image(new Texture("pausebackground.png"));
        tableBackground.setPosition(Defenders.V_WIDTH/2-tableBackground.getWidth()/2,Defenders.V_HEIGHT/2-tableBackground.getHeight()/3);



        Image playBtnImg = new Image(new Texture("pauseplaybutton.png"));
       /*
        playBtnImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("","BACK TO GAME CLICKED");

                playPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playPressed = false;
            }
        });
*/
        Image menuBtnImg = new Image(new Texture("pausebacktomenubutton.png"));
  /*
        menuBtnImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menuPressed = false;
            }
        });


*/
        table.add(playBtnImg).pad(40,40,40,40);
        table.add(menuBtnImg).pad(40,40,40,40);

        stage.addActor(tableBackground);
        stage.addActor(table);

    }

    public void draw(){
        stage.draw();
    }

    public boolean isPlayPressed() {
        return playPressed;
    }

    public boolean isMenuPressed() {
        return menuPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }


}