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
 * Created by Dor on 01/24/18.
 */



public class EndGameScreen implements Screen {
    private Viewport viewport;
    private Stage stage,backStage;
    private Game game;
    private Label gameOverLabel,newHighScoreLabel,scoreLabel,scoreLabelInNumber,creditsLabel
            ,credit1Name,credit1Info,credit2Name,credit2Info,credit3Name,credit3Info,
            credit4Name,credit4Info;
    private Skin skin;
    private Table table,table2,table3,table4,table5,creditTable;
    private TextArea userNameTextArea;
    private TextButton getUserNameBtn;
    private Image backgroundTexture,tableBackground,backToMenuBtn,leaderBoardBtn,restartGame,crown,ray;
    private ExtendViewport backViewPort;
    private boolean isHighScore;
    private int score;
    private AssetManager manager;

    public EndGameScreen(final Game game, final int score, final AssetManager manager) {
        this.game = game;
        this.score = score;
        this.manager = manager;
        viewport = new FitViewport(Defenders.V_WIDTH, Defenders.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((Defenders) game).batch);
        backViewPort = new ExtendViewport(Defenders.V_WIDTH, Defenders.V_HEIGHT);
        backStage = new Stage(backViewPort);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        isHighScore = false;
        initBackGround();
        initBtns();
        initRecordCrown();
        initLabels();
        initTables();
    }
    public void initBackGround(){
        backgroundTexture = new Image(new Texture("background.png"));
        backgroundTexture.setFillParent(true);
        tableBackground= new Image(new Texture("gameoverpanel.png"));
        tableBackground.setScale(0.8f);
        tableBackground.setPosition(Defenders.V_WIDTH/2/Defenders.PPM,(Defenders.V_HEIGHT/15));
    }
    public void initBtns(){
        backToMenuBtn = new Image(new Texture("endgamebacktomenubtn.png"));
        leaderBoardBtn = new Image(new Texture("endgameleaderboardbtn.png"));
        restartGame = new Image(new Texture("endgamerestartgamebtn.png"));
    }
    public void initRecordCrown(){
        crown= new Image(new Texture("highscorecrown.png"));
        crown.setOrigin(crown.getWidth()/2,crown.getHeight()/2);
        ray= new Image(new Texture("ray.png"));
        ray.setOrigin(ray.getWidth()/2,ray.getHeight()/2);
    }
    public void initTables(){
        initRecordTable();
        initSubBtnsTable();
        initCrownTable();
        initCreditTable();
    }
    public void initRecordTable(){
        table = new Table();
        table.center().padBottom(150);
        table.padTop(150);
        table.setFillParent(true);

        table5 = new Table();
        table5.center().padBottom(300);
        table5.setFillParent(true);

        if(Defenders.handler.isHighScore(score)) {
            table5.row();
            table5.add(newHighScoreLabel).top().uniform();
        }

        else{
            table5.add(gameOverLabel).top().uniform();
        }

        table.row();
        table.add(scoreLabel).uniform().padBottom(50f).padTop(100f);
        table.add(scoreLabelInNumber).uniform().padBottom(50f).padTop(100f);
        table.row();

        if(Defenders.handler.isHighScore(score)) {
            table.add(getUserNameBtn).padBottom(100f).uniform().fillX();
            table.add(userNameTextArea).padBottom(100f).uniform().fillX().fillY();
        }

        table.row();

        addNewRecordListeners();
    }
    public void addNewRecordListeners(){
        getUserNameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Defenders.handler.addPlayerScoreToDataBase(userNameTextArea.getText(), score);

                table.removeActor(getUserNameBtn);
                table.removeActor(userNameTextArea);

            }
        });


        userNameTextArea.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userNameTextArea.setText("");
            }
        });
    }
    public void initSubBtnsTable(){
        table2 = new Table().bottom().padBottom(40);
        table2.setFillParent(true);

        table2.add(backToMenuBtn).padBottom(10f).padRight(50).center();
        table2.add(restartGame).padBottom(10f).center();
        table2.add(leaderBoardBtn).padBottom(10f).padLeft(50).center();
        addSubBtnsListener();
    }
    public void addSubBtnsListener(){
        backToMenuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen((Defenders)game,manager));
                dispose();
            }
        });

        restartGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new PlayScreen((Defenders) game,Defenders.FIRST_LEVEL,Defenders.STARTING_SCORE,Defenders.STARTING_LIVES,manager));
                dispose();
            }
        });

        leaderBoardBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new LeaderBoardScreen(game,manager));
                dispose();
            }
        });
    }
    public void initCrownTable(){
        table4 = new Table().top();
        table3 = new Table().top();

        if(Defenders.handler.isHighScore(score)) {

            table4.add(ray).top();
            table4.setFillParent(true);

            table3.add(crown).top();
            table3.setFillParent(true);
        }
    }
    public void initLabels(){
        initMainLabels();
        initCreditsLabels();
    }
    public void initMainLabels(){
        gameOverLabel = new Label("GAME OVER", skin);
        gameOverLabel.setFontScale(1.5f);

        newHighScoreLabel = new Label("NEW HIGH SCORE", skin);
        newHighScoreLabel.setFontScale(1.5f);

        scoreLabel = new Label("Your Score is:", skin);
        scoreLabel.setFontScale(1.5f);
        scoreLabelInNumber = new Label(String.format("%06d", score), skin);
        scoreLabelInNumber.setFontScale(1.5f);


        getUserNameBtn = new TextButton("Submit", skin);
        getUserNameBtn.getLabel().setFontScale(1.5f);


        userNameTextArea = new TextArea("Enter Your Name", skin);
        userNameTextArea.setMaxLength(10);
    }
    public void initCreditsLabels(){
        creditsLabel = new Label("Credits:", skin);

        credit1Name = new Label("Perla Yehudai", skin);
        credit1Info = new Label("Main menu design", skin);

        credit2Name = new Label("Spriters-Resource", skin);
        credit2Info = new Label("Monsters design", skin);

        credit3Name = new Label("Riot Games", skin);
        credit3Info = new Label("Player design", skin);

        credit4Name = new Label("GraphicBurger", skin);
        credit4Info = new Label("Menus design", skin);
    }
    public void initCreditTable(){
        creditTable = new Table().bottom().padBottom(150);
        creditTable.defaults().width(150);
        creditTable.setFillParent(true);
        creditTable.add(creditsLabel);
        creditTable.row();
        creditTable.add(credit1Name);
        creditTable.add(credit1Info);
        creditTable.row();
        creditTable.add(credit2Name);
        creditTable.add(credit2Info);
        creditTable.row();
        creditTable.add(credit3Name);
        creditTable.add(credit3Info);
        creditTable.row();
        creditTable.add(credit4Name);
        creditTable.add(credit4Info);

    }
    @Override
    public void show() {
        backStage.addActor(backgroundTexture);
        backStage.addActor(table4);
        backStage.addActor(table3);
        backStage.addActor(tableBackground);
        stage.addActor(table5);
        stage.addActor(table);
        stage.addActor(table2);
        stage.addActor(creditTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        backStage.draw();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        backViewPort.update(width,height);
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
        backStage.dispose();
        stage.dispose();
    }
}
