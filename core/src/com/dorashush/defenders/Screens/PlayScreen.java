package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Scenes.Hud;
import com.dorashush.defenders.Sprites.Ball;
import com.dorashush.defenders.Sprites.Defender;
import com.dorashush.defenders.Sprites.Dragon;
import com.dorashush.defenders.Sprites.SimpleBall;
import com.dorashush.defenders.Tools.B2WorldCreator;
import com.dorashush.defenders.Tools.WorldContactListener;

/**
 * Created by Dor on 01/22/18.
 */

public class PlayScreen implements Screen {
    public static final int SCREEN_WIDTH = 480;
    public static final int SCREEN_HEIGHT = 800;
    public static final int TIME_BETWEEN_BALL_SPAWN = 4;

    private Defenders game;
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d Vars
    private World world;
    private Box2DDebugRenderer b2dr;
    private Defender player;
    private Dragon dragon;
    private SimpleBall simpleBall;//for ball testing
    private Vector2 positionToSpawnBall;

    //Ball control
    Array<SimpleBall> ballArray;
    private float timeCount;

    public PlayScreen(Defenders game){
        atlas = new TextureAtlas("player_and_enemy");

        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Defenders.V_WIDTH/ Defenders.PPM,Defenders.V_HEIGHT/ Defenders.PPM,gameCam);
        gamePort.apply();
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("stage.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ Defenders.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);


        //Box2d
        world = new World(new Vector2(0,0),true);


        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(this);
        //Ball Control
        ballArray = new Array<SimpleBall>();
        timeCount = 0;


        player = new Defender(this);
        dragon = new Dragon(this,240/ Defenders.PPM,750/Defenders.PPM);
       // simpleBall = new SimpleBall(this,dragon.getX(),dragon.getY()-dragon.getHeight()/2); //for ball testing

        world.setContactListener(new WorldContactListener());
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
        Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        if((touchPos.x > Defenders.V_WIDTH/2) && player.b2body.getLinearVelocity().x<=2){ //move right
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0-0.1f),player.b2body.getWorldCenter(),true);
        }

        else if((touchPos.x < Defenders.V_WIDTH/2) && player.b2body.getLinearVelocity().x>=-2){ //move left
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,-0.1f),player.b2body.getWorldCenter(),true);
        }
    }
    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);

        player.update(dt);
        hud.update(dt);

        //The to find better way to update this
        dragon.update(dt);
        //////////////////////////////////

        timeCount += dt;
        if(timeCount>=TIME_BETWEEN_BALL_SPAWN) {
            ballArray.add(new SimpleBall(this,dragon.getX()-dragon.getWidth()/Defenders.PPM,dragon.getY()-dragon.getHeight()/Defenders.PPM));
            timeCount=0;
        }

        for (SimpleBall ball : ballArray){
            if(ball.removed) {
                //world.destroyBody(ball.b2body);
                ballArray.removeValue(ball,true);
            }
            ball.update(dt);
        }

      //  simpleBall.update(dt); //for ball testing

        ///////////////////////////
        game.batch.setProjectionMatrix(gameCam.combined);
        renderer.setView(gameCam);

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        b2dr.render(world,gameCam.combined);


        game.batch.begin();
        player.draw(game.batch);
        dragon.draw(game.batch);
       // simpleBall.draw(game.batch);// for ball testing

        for (SimpleBall ball : ballArray){
           if(ball!=null)
             ball.draw(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
    gamePort.update(width,height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
