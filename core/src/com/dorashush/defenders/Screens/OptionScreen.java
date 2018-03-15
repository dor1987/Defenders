package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;


/**
 * Created by Dor on 02/26/18.
 */

public class OptionScreen implements Screen {
    private Viewport viewport;
    private Stage stage,backStage;
    private Game game;
    private Skin skin;
    private Table table,table2;
    private ExtendViewport backViewPort;
    private Image backgroundTexture,tableBackground,backBtn;
    private Label soundLabel,controlLabel,vibreationLabel,cheatLabel;
    private TextButton soundOnButton,soundOffButton,singleClickButton,fullControlButton,vibreationOnButton
            ,vibreationOffButton,submitCheatButton;
    ButtonGroup soundButtonGroup,controlButtonGroup,vibreationButtonGroup;
    private TextArea cheatTextArea;
    private AssetManager manager;


    public OptionScreen(final Game game, final AssetManager manager){
        this.game = game;
        this.manager= manager;
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,((Defenders)game).batch);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        initBackground();

        viewport.apply();
        backViewPort.apply();

        initTitles();
        initButtons();
        initTabels();
        addListeners();
    }

    public void addListeners(){
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

    public void initTabels(){
        initMainBtnsTable();
        initSubBtnsTable();
    }

    public void initMainBtnsTable(){
        table = new Table().center().top().padTop(120f);
        table.setFillParent(true);

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
    }

    public void initSubBtnsTable(){
        table2 = new Table().bottom();
        table2.setFillParent(true);
        table2.add(backBtn).padBottom(10f).center();

    }

    public void initTitles(){
        soundLabel = new Label("Sound:",skin);
        soundLabel.setFontScale(1.5f,1.5f);
        controlLabel= new Label("Control:",skin);
        controlLabel.setFontScale(1.5f,1.5f);
        vibreationLabel= new Label("Vibration:",skin);
        vibreationLabel.setFontScale(1.5f,1.5f);
        cheatLabel = new Label("Cheats:",skin);
        cheatLabel.setFontScale(1.5f,1.5f);
    }

    public void initButtons(){
        backBtn = new Image(new Texture("back.png"));
        initSoundBtns();
        initControlBtns();
        initVibrationBtns();
        initCheatArea();

    }
    public void initSoundBtns(){
        soundOnButton = new TextButton("ON", skin,"toggle");
        soundOffButton = new TextButton("OFF", skin,"toggle");
        soundButtonGroup = new ButtonGroup(soundOnButton, soundOffButton);

        if(Defenders.VOLUME==1)
            soundButtonGroup.setChecked("ON");
        else if(Defenders.VOLUME==0){
            soundButtonGroup.setChecked("OFF");
        }
    }
    public void initControlBtns(){
        singleClickButton = new TextButton("Single Click", skin,"toggle");
        fullControlButton = new TextButton("Full Control", skin,"toggle");
        controlButtonGroup = new ButtonGroup(singleClickButton, fullControlButton);

        if(Defenders.FULL_CONTROL==true)
            controlButtonGroup.setChecked("Full Control");
        else if(Defenders.FULL_CONTROL==false){
            controlButtonGroup.setChecked("Single Click");
        }
    }
    public void initVibrationBtns(){
        vibreationOnButton = new TextButton("ON", skin,"toggle");
        vibreationOffButton = new TextButton("OFF", skin,"toggle");
        vibreationButtonGroup = new ButtonGroup(vibreationOnButton, vibreationOffButton);

        if(Defenders.VIBRATION==true)
            vibreationButtonGroup.setChecked("ON");
        else if(Defenders.VIBRATION==false){
            vibreationButtonGroup.setChecked("OFF");
        }
    }
    public void initCheatArea(){
        cheatTextArea = new TextArea("", skin);
        submitCheatButton=new TextButton("Enter", skin);
    }
        public void initBackground(){
        backViewPort = new ExtendViewport( Defenders.V_WIDTH, Defenders.V_HEIGHT );
        backStage = new Stage(backViewPort);
        backgroundTexture = new Image(new Texture("background.png"));
        backgroundTexture.setFillParent(true);
        tableBackground= new Image(new Texture("optionspanel.png"));
        tableBackground.setFillParent(true);
    }

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
