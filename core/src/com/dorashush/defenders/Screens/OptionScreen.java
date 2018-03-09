package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;

import java.util.ArrayList;

/**
 * Created by Dor on 02/26/18.
 */

public class OptionScreen implements Screen {
    private Viewport viewport;
    private Stage stage,backStage;
    private Game game;
    private Skin skin;
    private Table table;
    private Table table2;

    private ExtendViewport backViewPort;
    private Image backgroundTexture;
    private Image tableBackground;

    private TextButton line1;
/*
    private CheckBox checkBox = new CheckBox("one", skin);
    private CheckBox checkBox2 = new CheckBox("two", skin);
    private CheckBox checkBox3 = new CheckBox("three", skin);
    private ButtonGroup buttonGroup = new ButtonGroup(checkBox, checkBox2, checkBox3);
*/
    private Label soundLabel;
    private TextButton soundOnButton;
    private  TextButton soundOffButton;
    ButtonGroup soundButtonGroup;

    private Label controlLabel;
    private TextButton singleClickButton;
    private TextButton fullControlButton;
    ButtonGroup controlButtonGroup;

    private Label vibreationLabel;
    private TextButton vibreationOnButton;
    private TextButton vibreationOffButton;
    ButtonGroup vibreationButtonGroup;


    private Label cheatLabel;
    private TextArea cheatTextArea;
    private TextButton submitCheatButton;


    private Image backBtn;



    public OptionScreen(final Game game, final AssetManager manager){
        this.game = game;
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,((Defenders)game).batch);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        backViewPort = new ExtendViewport( Defenders.V_WIDTH, Defenders.V_HEIGHT );
        backStage = new Stage(backViewPort);
        backgroundTexture = new Image(new Texture("background.png"));
        backgroundTexture.setFillParent(true);
        tableBackground= new Image(new Texture("optionspanel.png"));
        tableBackground.setFillParent(true);
        viewport.apply();
        backViewPort.apply();

        soundLabel = new Label("Sound:",skin);
        soundLabel.setFontScale(1.5f,1.5f);
        controlLabel= new Label("Control:",skin);
        controlLabel.setFontScale(1.5f,1.5f);
        vibreationLabel= new Label("Vibration:",skin);
        vibreationLabel.setFontScale(1.5f,1.5f);
        cheatLabel = new Label("Cheats:",skin);
        cheatLabel.setFontScale(1.5f,1.5f);

        backBtn = new Image(new Texture("back.png"));



        soundOnButton = new TextButton("ON", skin,"toggle");
        soundOffButton = new TextButton("OFF", skin,"toggle");
        soundButtonGroup = new ButtonGroup(soundOnButton, soundOffButton);

        if(Defenders.VOLUME==1)
            soundButtonGroup.setChecked("ON");
        else if(Defenders.VOLUME==0){
            soundButtonGroup.setChecked("OFF");
        }


        singleClickButton = new TextButton("Single Click", skin,"toggle");
        fullControlButton = new TextButton("Full Control", skin,"toggle");
        controlButtonGroup = new ButtonGroup(singleClickButton, fullControlButton);

        if(Defenders.FULL_CONTROL==true)
            controlButtonGroup.setChecked("Full Control");
        else if(Defenders.FULL_CONTROL==false){
            controlButtonGroup.setChecked("Single Click");
        }


        vibreationOnButton = new TextButton("ON", skin,"toggle");
        vibreationOffButton = new TextButton("OFF", skin,"toggle");
        vibreationButtonGroup = new ButtonGroup(vibreationOnButton, vibreationOffButton);

        if(Defenders.VIBRATION==true)
            vibreationButtonGroup.setChecked("ON");
        else if(Defenders.VIBRATION==false){
            vibreationButtonGroup.setChecked("OFF");
        }


        cheatTextArea = new TextArea("", skin);
        submitCheatButton=new TextButton("Enter", skin);

        table = new Table().center().top().padTop(120f);
        table.setFillParent(true);

        // table.add(leaderBoardList);
        table.add(soundLabel).left().padBottom(10);
        table.row();

        table.add(soundOnButton).width(150).height(50);
        table.add(soundOffButton).width(150).height(50);
        table.row().padTop(50);

        table.add(controlLabel).left().padBottom(10);
        table.row();
        table.add(singleClickButton).width(150).height(50);
        table.add(fullControlButton).width(150).height(50);

        table.row().padTop(50);

        table.add(vibreationLabel).left().padBottom(10);
        table.row();
        table.add(vibreationOnButton).width(150).height(50);
        table.add(vibreationOffButton).width(150).height(50);

        table.row().padTop(50);

        table.add(cheatLabel).left().padBottom(10);
        table.row();
        table.add(cheatTextArea).width(150).height(50);
        table.add(submitCheatButton).width(150).height(50);



        table2 = new Table().bottom();
        table2.setFillParent(true);
        table2.add(backBtn).padBottom(10f).center();

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                game.setScreen(new MainMenuScreen((Defenders) game,manager));
                dispose();
            }
        });



        soundOnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Defenders.VOLUME=1f;
                Defenders.defendersSettings.putFloat("volume",1f);
                Defenders.defendersSettings.flush();
            }
        });

        soundOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Defenders.VOLUME=0;
                Defenders.defendersSettings.putFloat("volume",0f);
                Defenders.defendersSettings.flush();
            }
        });


        vibreationOnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Defenders.VIBRATION=true;
                Defenders.defendersSettings.putBoolean("vibration",true);
                Defenders.defendersSettings.flush();

            }
        });

        vibreationOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Defenders.VIBRATION=false;
                Defenders.defendersSettings.putBoolean("vibration",false);
                Defenders.defendersSettings.flush();
            }
        });


        fullControlButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Defenders.FULL_CONTROL=true;
                Defenders.defendersSettings.putBoolean("control",true);
                Defenders.defendersSettings.flush();

            }
        });

        singleClickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Defenders.FULL_CONTROL=false;
                Defenders.defendersSettings.putBoolean("control",false);
                Defenders.defendersSettings.flush();
            }
        });
    }

    //TODO add cheat funcionality

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        backStage.addActor(backgroundTexture);
        backStage.addActor(tableBackground);

        stage.addActor(table);
        stage.addActor(table2);

    }



        @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.act();
        backStage.draw();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);

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
        backStage.dispose();
    }
}
