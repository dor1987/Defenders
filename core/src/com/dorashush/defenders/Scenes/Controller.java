package com.dorashush.defenders.Scenes;

/**
 * Created by Dor on 02/20/18.
 */

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


public class Controller {
    Viewport viewport;
    Stage stage;
    boolean  leftPressed, rightPressed, pausePressed,bombedPressed,speedPressed,playPressed, menuPressed;
    OrthographicCamera cam;
    private static Integer amountOfBombs;
    static TextButton bombImg;
    private static Integer amountOfSpeeds;
    static TextButton speedImg;
    private Table table;
    private Table pauseTable;
    private Image tableBackground;
    public Controller(){
        cam = new OrthographicCamera();
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, cam);
        stage = new Stage(viewport, Defenders.batch);
        amountOfBombs = 0;
        amountOfSpeeds = 0;
/*
        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){

                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                }
                return true;
            }
        });
*/
        Gdx.input.setInputProcessor(stage);
/////////////
        pauseTable= new Table();
        pauseTable.center();
        pauseTable.setFillParent(true);
        pauseTable.setVisible(false);
        tableBackground = new Image(new Texture("pausebackground.png"));
        tableBackground.setPosition(Defenders.V_WIDTH/2-tableBackground.getWidth()/2,Defenders.V_HEIGHT/2-tableBackground.getHeight()/3);
        tableBackground.setVisible(false);



        Image playBtnImg = new Image(new Texture("pauseplaybutton.png"));
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

        Image menuBtnImg = new Image(new Texture("pausebacktomenubutton.png"));

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

        pauseTable.add(playBtnImg).pad(40,40,40,40);
        pauseTable.add(menuBtnImg).pad(40,40,40,40);


        ///////////////
        table = new Table();
        table.center().bottom().padBottom(100);
        table.setFillParent(true);

        Image rightImg = new Image(new Texture("right.png"));
        rightImg.setSize(76, 76);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("left.png"));
        leftImg.setSize(76, 76);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });


        final Image pauseImg = new Image(new Texture("pause.png"));
        pauseImg.setSize(76, 76);

        pauseImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("","PAUSE CLICKED");

                pausePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pausePressed = false;
            }
        });


        Drawable bombDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("bomb.png")));
        TextButton.TextButtonStyle bombTextButtonStyle = new TextButton.TextButtonStyle( bombDrawable, bombDrawable, bombDrawable, new BitmapFont() );
        bombImg = new TextButton( String.format("%02d",amountOfBombs) ,bombTextButtonStyle);
        bombImg.getLabel().setFontScale(1.5f);

        bombImg.setDisabled( true );
        bombImg.setSize(76,76);
        bombImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bombedPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bombedPressed = false;
            }
        });

        Drawable speedDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("speed.png")));
        TextButton.TextButtonStyle speedTextButtonStyle = new TextButton.TextButtonStyle( speedDrawable, speedDrawable, speedDrawable, new BitmapFont() );
        speedImg = new TextButton( String.format("%02d",amountOfSpeeds) ,speedTextButtonStyle);
        speedImg.getLabel().setFontScale(1.5f);

        speedImg.setDisabled( true );
        speedImg.setSize(76,76);
        speedImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                speedPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                speedPressed = false;
            }
        });


        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).padRight(10);
        table.add(speedImg).size(speedImg.getWidth(), speedImg.getHeight()).pad(0,10,0,10);
        table.add(pauseImg).size(pauseImg.getWidth(), pauseImg.getHeight()).pad(0,10,0,10);
        table.add(bombImg).size(bombImg.getWidth(), bombImg.getHeight()).pad(0,10,0,10);
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).padLeft(10);


        stage.addActor(table);
        stage.addActor(tableBackground);
        stage.addActor(pauseTable);
    }

    public void draw(){
        stage.draw();
    }

    public boolean isPausePressed() {
        return pausePressed;
    }

    public boolean isBombedPressed() {
        return bombedPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isSpeedPressed() {
        return speedPressed;
    }
    public static Integer getAmountOfBombs() {
        return amountOfBombs;
    }
    public static void setAmountOfBombs(Integer amountOfBombs) {
        Controller.amountOfBombs = amountOfBombs;
        bombImg.setText(String.format("%02d",amountOfBombs));
    }

    public static Integer getAmountOfSpeeds() {
        return amountOfSpeeds;
    }

    public static void setAmountOfSpeeds(Integer amountOfSpeeds) {
        Controller.amountOfSpeeds = amountOfSpeeds;
        speedImg.setText(String.format("%02d",amountOfSpeeds));
    }

    public boolean isPlayPressed() {
        return playPressed;
    }

    public boolean isMenuPressed() {
        return menuPressed;
    }

    public void setPauseMenuVisable(boolean state){
        pauseTable.setVisible(state);
        tableBackground.setVisible(state);
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}