package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Scenes.Controller;
import com.dorashush.defenders.Scenes.Hud;
import com.dorashush.defenders.Scenes.HudForEndLevel;
import com.dorashush.defenders.Sprites.Alien;
import com.dorashush.defenders.Sprites.AlienBall;
import com.dorashush.defenders.Sprites.Ball;
import com.dorashush.defenders.Sprites.Boss;
import com.dorashush.defenders.Sprites.BossBall;
import com.dorashush.defenders.Sprites.Defender;
import com.dorashush.defenders.Sprites.DinoRaider;
import com.dorashush.defenders.Sprites.DinoRaiderBall;
import com.dorashush.defenders.Sprites.Dragon;
import com.dorashush.defenders.Sprites.Enemy;
import com.dorashush.defenders.Sprites.BombPowerUp;
import com.dorashush.defenders.Sprites.ForestGhost;
import com.dorashush.defenders.Sprites.ForestGhostBall;
import com.dorashush.defenders.Sprites.ForestWitch;
import com.dorashush.defenders.Sprites.IceDino;
import com.dorashush.defenders.Sprites.IceDinoBall;
import com.dorashush.defenders.Sprites.LifePowerUp;
import com.dorashush.defenders.Sprites.LightingPowerUp;
import com.dorashush.defenders.Sprites.NormalBull;
import com.dorashush.defenders.Sprites.NormalBullBall;
import com.dorashush.defenders.Sprites.PointsPowerUp;
import com.dorashush.defenders.Sprites.PowerUp;
import com.dorashush.defenders.Sprites.SimpleBall;
import com.dorashush.defenders.Sprites.SpeedChangingBall;
import com.dorashush.defenders.Sprites.WingedBull;
import com.dorashush.defenders.Sprites.WingedBullFireBall;
import com.dorashush.defenders.Tools.B2WorldCreator;
import com.dorashush.defenders.Tools.LevelsInfoData;
import com.dorashush.defenders.Tools.WorldContactListener;

import java.util.Random;

/**
 * Created by Dor on 01/22/18.
 */

public class PlayScreen implements Screen {
    public static boolean godMode = false;

    public enum GameStatus {
        LOOSE,
        WIN,
        MID_GAME,
        PAUSED,
    }

    private Defenders game;
    private TextureAtlas atlas;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private HudForEndLevel hudForEndLevel;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //Box2d Vars
    private World world;
    private Box2DDebugRenderer b2dr;
    private Defender player;
    private Enemy enemy;
    //Ball control
    private Array<Ball> ballArray;
    private float ballTimeCount;
    //Power Ups
    private Array<PowerUp> powerUpArray;
    private float powerUpTimeCount;
   // private boolean speed;
    private boolean bomb;
    private int godModeCheatCounter;
    private float godModeCheatCounterTimer;
    //Timers
    private float bombCoolDownTimer,endGamewindowTimer,speedCoolDownTimer;
    //Stage managment
    private LevelsInfoData levelManager;
    private int[] currentLevelInfo;
    private GameStatus gameStatus;
    private int enemyType,ballType,powerUpsType,levelNum,timeBetweenBalls,amountOfBallsPerShoot,timeBetweenPowerUps,amountOfLevel;
    private Controller controller;
    //Enemy hp bar
    private Texture blank;
    //Player movment speeds
    private Vector2 normalSpeedLeft,lightingSpeedLeft,normalSpeedRight,lightingSpeedRight,speedToUseRight,speedToUseLeft;
    //sound
    AssetManager manager;
    boolean isFirstTimeSound;
    public PlayScreen(Defenders game , int levelNumber , int score , int lives, AssetManager manager){
        atlas = new TextureAtlas("final_animation_for_game.atlas");
        this.manager = manager;
        this.game = game;
        isFirstTimeSound =true;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Defenders.V_WIDTH/ Defenders.PPM,Defenders.V_HEIGHT/ Defenders.PPM,gameCam);
        gamePort.apply();
        hud = new Hud(game.batch);
        hudForEndLevel = new HudForEndLevel(game.batch,manager);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("stage2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ Defenders.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        endGamewindowTimer =0;
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(this);
        ballAndPowerIniter();
        player = new Defender(this, manager);
        gameStatus=GameStatus.MID_GAME;
        levelInfoInit(levelNumber);
        hudSet(score,lives);
        enemy = initlizeEnemy(enemyType);
        blank = new Texture("blank.png");
        controller = new Controller();
        playerMovementSpeedInit();
        world.setContactListener(new WorldContactListener());
        playStarGameSound();
    }
    public TextureAtlas getAtlas(){
        return atlas;
    }
    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        bombCoolDownTimer+=dt;
        speedCoolDownTimer+=dt;
        godModeCheatCounterTimer+=dt;


        if(gameStatus == GameStatus.MID_GAME) {
            if (controller.isSpeedPressed()) {
                if (controller.getAmountOfSpeeds() > 0 && speedCoolDownTimer >= 8) {
                    manager.get("sound/notsofast.ogg",Sound.class).play(Defenders.VOLUME);

                    speedToUseRight = lightingSpeedRight;
                    speedToUseLeft = lightingSpeedLeft;
                    controller.setAmountOfSpeeds(controller.getAmountOfSpeeds() - 1);

                    speedCoolDownTimer = 0;
                }
            } else if (speedCoolDownTimer >= 8) {
                speedToUseRight = normalSpeedRight;
                speedToUseLeft = normalSpeedLeft;
            }

            if (controller.isRightPressed()) {
                player.b2body.setLinearVelocity(speedToUseRight);
            } else if (controller.isLeftPressed()) {
                player.b2body.setLinearVelocity(speedToUseLeft);
            } else {
                if (Defenders.FULL_CONTROL == true) {
                    player.b2body.setLinearVelocity(new Vector2(0, 0));
                }
            }

            if (controller.isBombedPressed()) {
                if (controller.getAmountOfBombs() > 0 && bombCoolDownTimer >= 2) {
                    setBomb(true);
                   manager.get("sound/bomb.mp3",Sound.class).play(Defenders.VOLUME);
                    if(Defenders.VIBRATION){
                        Gdx.input.vibrate(300);
                    }

                    controller.setAmountOfBombs(controller.getAmountOfBombs() - 1);
                    bombCoolDownTimer = 0;
                }

            }

            if (godModeCheatCounterTimer >= 3) {
                godModeCheatCounter = 0;
                godModeCheatCounterTimer = 0;
            }

            if (controller.isPausePressed()) {
                godModeCheatCounter += 1;
                if (godModeCheatCounter >= 5) {
                    godMode = !godMode;
                    godModeCheatCounter = 0;
                }

                if (gameStatus != GameStatus.PAUSED)
                    pause();
            }
        }

        else if(gameStatus==GameStatus.PAUSED){
            if(controller.isPlayPressed()){
                gameStatus=GameStatus.MID_GAME;

            }

            else if(controller.isMenuPressed()){
                gameStatus = GameStatus.LOOSE;
            }
        }
    }

    public void update(float dt){
            handleInput(dt);
            world.step(1 / 60f, 6, 2);
               player.update(dt);
               hud.update(dt);
               enemy.update(dt);
               //add balls time counter
               ballTimeCount += dt;
               //boss
               if (enemyType == 666)
                   initBoss();

                ballAdder();
                powerUpsAdder(dt);
                ballAndPowerUpsupdate(dt);

               game.batch.setProjectionMatrix(gameCam.combined);
               renderer.setView(gameCam);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(gameStatus==GameStatus.MID_GAME) {
            update(delta);
        }

        //render the game map
        renderer.render();
        game.batch.setProjectionMatrix(gameCam.combined);
        gamePlayRender();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        controller.draw();

        hud.stage.draw();
        endGameIfOver(delta);
        handlePause(delta);
    }

    public void endLevelHudRender(){
        //update end level hud before drawing
        endLevelSound(isFirstTimeSound);
        hudForEndLevel.setGameStatus(gameStatus);
        hudForEndLevel.setScore(Hud.getScore());
        hudForEndLevel.setTimeLeftBonus(Hud.getTimeLeft());
        hudForEndLevel.setTotalScore(Hud.getScore()+Hud.getTimeLeft());
        hudForEndLevel.setAmountOfStars(Hud.getTimeLeft(),gameStatus);
        game.batch.setProjectionMatrix(hudForEndLevel.stage.getCamera().combined);
        hudForEndLevel.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
        controller.resize(width,height);
    }

    @Override
    public void pause() {
        if(gameStatus!= GameStatus.WIN && gameStatus!= GameStatus.LOOSE)
            gameStatus=GameStatus.PAUSED;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        hudForEndLevel.dispose();
    }
    public int generateNumber(int maxNum) {
        Random random = new Random();
        int result = random.nextInt(maxNum+1); //to avoid maxnum been 0
        return result;
    }
    public Enemy initlizeEnemy(int enemyToInitilize){
        Enemy enemy;

        switch (enemyToInitilize){
            case 0:
                enemy = new WingedBull(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            case 1:
                enemy = new Dragon(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            case 2:
                enemy = new DinoRaider(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            case 3:
                enemy = new Alien(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            case 4:
                enemy = new ForestWitch(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            case 5:
                enemy = new ForestGhost(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            case 6:
                enemy = new NormalBull(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            case 7:
                enemy = new IceDino(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            case 666:
                enemy = new Boss(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;

            default:
                enemy = new Dragon(this,240/ Defenders.PPM,750/Defenders.PPM);
                break;
        }

        return enemy;
    }
    public Ball initlizeBall(int ballToInitilize){
        Ball ball;
        switch (ballToInitilize){
            case 0:
                ball = new WingedBullFireBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;

            case 1:
                ball = new SimpleBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;

            case 2:
                ball = new DinoRaiderBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;

            case 3:
                ball = new AlienBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;

            case 4:
                ball = new SpeedChangingBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;

            case 5:
                ball = new ForestGhostBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;

            case 6:
                ball = new NormalBullBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;

            case 7:
                ball = new IceDinoBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;

            case 666:
                if(enemy.getHealthBar()>0.7){
                    ball = initlizeBall(generateNumber(4)-1); //init random ball for phase 1
                }

                else if(enemy.getHealthBar()>0.4){
                    ball = initlizeBall(generateNumber(4)+3); //init random ball for phase 1
                }
                else{
                    ball = new BossBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                }

                break;


            default:
                ball = new SimpleBall(this,enemy.getX()+(enemy.getWidth()*50)/Defenders.PPM,enemy.getY()-enemy.getHeight()/Defenders.PPM);
                break;
        }
        return ball;
    }
    public PowerUp initlizePowerUp(int powerUpToInitilize){
        PowerUp powerUp;
        powerUpToInitilize = generateNumber(powerUpToInitilize);
        switch (powerUpToInitilize){
            case 0:
                powerUp = new BombPowerUp(this,manager);
                break;
            case 1:
                powerUp = new LifePowerUp(this,manager);
                break;

            case 2:
                powerUp = new PointsPowerUp(this);
                break;

            case 3:
                powerUp = new LightingPowerUp(this);
                break;

            default:
                powerUp = new BombPowerUp(this,manager);
                break;
        }
        return powerUp;
    }
    public void endGameIfOver(float delta){
        if(gameStatus!=GameStatus.MID_GAME && gameStatus!=GameStatus.PAUSED){
            endLevelHudRender();
            waitInputSetNextScreen(delta);
        }
    }
    public void gamePlayRender(){
        game.batch.begin();
        player.draw(game.batch);
        enemy.draw(game.batch);

        checkIfLost();
        checkIfWin();

        if(bomb)
            removeAllBalls();

        ballRemoveControl();
        powerUpRemoveControl();
        enemyHpBarControl();
        game.batch.end();
    }
    public void checkIfLost(){
        for (Ball ball : ballArray) {
            if (ball.hitedTheVillage) {
                hud.reduceLive();
            }
            if(hud.getLives()<=0)
                gameStatus=GameStatus.LOOSE;
        }
    }
    public void checkIfWin(){
        if(enemy.removed){
            gameStatus=GameStatus.WIN;
        }
    }
    public void waitInputSetNextScreen(float dt) {
        endGamewindowTimer+=dt;

        if(Gdx.input.justTouched()){
            if(gameStatus==GameStatus.WIN && endGamewindowTimer >= 2){
                if (amountOfLevel == levelNum)//check if last stage
                    game.setScreen(new EndGameScreen(game, Hud.getScore()+Hud.getTimeLeft(),manager));
                else
                    game.setScreen(new PlayScreen(game, levelNum, Hud.getScore()+Hud.getTimeLeft(),Hud.getLives(),manager)); //move to next level
                dispose();
            }

            else if(gameStatus==GameStatus.LOOSE && endGamewindowTimer >= 2){
                game.setScreen(new EndGameScreen(game, Hud.getScore(),manager));
                dispose();
            }

            else if(gameStatus==GameStatus.PAUSED){
                    gameStatus=GameStatus.MID_GAME;
            }

        }
    }
    public void removeAllBalls(){
        for (Ball ball : ballArray) {
                ball.removeFromGame();
        }
        bomb = false;
    }
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }
    public void endLevelSound(boolean isFirstTime) {
        if (isFirstTime) {
            if (gameStatus == PlayScreen.GameStatus.LOOSE) {
                manager.get("sound/motherdontlose.ogg", Sound.class).play(Defenders.VOLUME);
            } else if (gameStatus == PlayScreen.GameStatus.WIN) {
                int temp = generateNumber(3);
                switch (temp) {
                    case 1:
                        manager.get("sound/braumlivesanotherday.ogg", Sound.class).play(Defenders.VOLUME);
                        break;
                    case 2:
                        manager.get("sound/butuhaveme.ogg", Sound.class).play(Defenders.VOLUME);
                        break;

                    case 3:
                        manager.get("sound/ucallbraum.ogg", Sound.class).play(Defenders.VOLUME);
                        break;

                        default:
                            manager.get("sound/ucallbraum.ogg", Sound.class).play(Defenders.VOLUME);
                            break;
                }

            }
            isFirstTimeSound=false;
        }
    }
    public void playStarGameSound(){
        int temp =generateNumber(6);

        switch (temp) {
            case 1:
                manager.get("sound/weareabouttobegin.ogg",Sound.class).play(Defenders.VOLUME);
                break;
            case 2:
                manager.get("sound/braumishere.ogg", Sound.class).play(Defenders.VOLUME);
                break;

            case 3:
                manager.get("sound/myshieldishereforu.ogg", Sound.class).play(Defenders.VOLUME);
                break;
            case 4:
                manager.get("sound/myshieldismysword.ogg", Sound.class).play(Defenders.VOLUME);
                break;
            case 5:
                manager.get("sound/showmeyourbest.ogg", Sound.class).play(Defenders.VOLUME);
                break;

            case 6:
                manager.get("sound/standbehindbraum.ogg", Sound.class).play(Defenders.VOLUME);
                break;

            default:
                manager.get("sound/standbehindbraum.ogg", Sound.class).play(Defenders.VOLUME);
                break;
        }
    }
    public void initBoss(){
        if (enemy.getHealthBar() > 0.7) {
            timeBetweenBalls = 4;
        } else if (enemy.getHealthBar() > 0.4) {
            timeBetweenBalls = 4;
            amountOfBallsPerShoot = 2;
        } else {
            timeBetweenBalls = 3;
            amountOfBallsPerShoot = 3;
        }
    }
    public void ballAdder(){
        if (ballTimeCount >= timeBetweenBalls) {
            for (int i = 0; i < amountOfBallsPerShoot; i++)
                ballArray.add(initlizeBall(ballType));
            ballTimeCount = 0;
        }
    }
    public void powerUpsAdder(float dt){
        powerUpTimeCount += dt;
        if (powerUpTimeCount >= timeBetweenPowerUps) {
            powerUpArray.add(initlizePowerUp(powerUpsType));
            powerUpTimeCount = 0;
        }

    }
    public void ballAndPowerUpsupdate(float dt){
        for (Ball ball : ballArray) {
            ball.update(dt);
        }

        for (PowerUp powerUp : powerUpArray) {
            powerUp.update(dt);
        }
    }
    public void handlePause(float delta){
        if(gameStatus==GameStatus.PAUSED){
            controller.setPauseMenuVisable(true);
            handleInput(delta);
        }

        else if(gameStatus!=GameStatus.PAUSED){
            controller.setPauseMenuVisable(false);
        }
    }
    public void ballRemoveControl(){
        for (Ball ball : ballArray) {
            if (ball.removed)
                ballArray.removeValue(ball, true);

            if (ball != null) {
                ball.draw(game.batch);
            }
        }
    }
    public void powerUpRemoveControl(){
        for (PowerUp powerUp : powerUpArray) {
            if (powerUp.removed)
                powerUpArray.removeValue(powerUp, true);

            if (powerUp != null) {
                powerUp.draw(game.batch);
            }
        }
    }
    public void enemyHpBarControl(){
        game.batch.setColor(Color.BLACK);
        game.batch.draw(blank,0,(Defenders.V_HEIGHT-7)/Defenders.PPM,Defenders.V_WIDTH/Defenders.PPM,1/Defenders.PPM);
        game.batch.setColor(Color.WHITE);

        if(enemy.getHealthBar() > 0.7f){
            game.batch.setColor(Color.GREEN);
        }
        else if(enemy.getHealthBar() > 0.4f)
            game.batch.setColor(Color.ORANGE);
        else
            game.batch.setColor(Color.RED);


        game.batch.draw(blank,0,(Defenders.V_HEIGHT-6)/Defenders.PPM,Defenders.V_WIDTH*enemy.getHealthBar()/Defenders.PPM,6/Defenders.PPM);
        game.batch.setColor(Color.WHITE);
    }
    public void ballAndPowerIniter(){
        bomb = false;
        godModeCheatCounter=0;
        godModeCheatCounterTimer=0;

        ballArray = new Array<Ball>();
        powerUpArray = new Array<PowerUp>();
        ballTimeCount = 0;
        powerUpTimeCount=0;
    }
    public void playerMovementSpeedInit(){
        normalSpeedLeft = new Vector2(-1.5f,0);
        lightingSpeedLeft = new Vector2(-2.5f,0);
        normalSpeedRight = new Vector2(1.5f,0);
        lightingSpeedRight = new Vector2(2.5f,0);
        speedToUseLeft= normalSpeedLeft;
        speedToUseRight=normalSpeedRight;
    }
    public void hudSet(int score, int lives){
        Hud.levelNumber(levelNum);
        Hud.addScore(score); //to carry score from last level
        Hud.setLives(lives);
    }
    public void levelInfoInit(int levelNumber){
        levelManager = new LevelsInfoData();
        currentLevelInfo = levelManager.getCurrentLevelInfo(levelNumber);
        enemyType = currentLevelInfo[0];
        ballType = currentLevelInfo[1];
        powerUpsType = currentLevelInfo[2];
        levelNum = currentLevelInfo[3];
        amountOfLevel = currentLevelInfo[4];
        timeBetweenBalls=currentLevelInfo[5];
        amountOfBallsPerShoot = currentLevelInfo[6];
        timeBetweenPowerUps = currentLevelInfo[7];
    }
}

