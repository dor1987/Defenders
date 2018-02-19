package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;
import com.dorashush.defenders.Tools.BodyUserData;

/**
 * Created by Dor on 01/22/18.
 */

public class Defender extends Sprite{
    public enum State {WALKING,STANDING};
    State currentState;
    State previousState;
    private Animation defenderWalk;
    private boolean walkingRight;
    private float stateTimer;


    public World world;
    public Body b2body;
    private TextureRegion defenderStand;

    private BodyUserData bodyUserData;

    //power ups


    public Defender(PlayScreen screen){
        super(screen.getAtlas().findRegion("playersheet"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        walkingRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i<10 ;i++){
         //   frames.add(new TextureRegion(getTexture(),655+i*107,106,106,155));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("playersheet"), i *107,0,106,155));
        }
        defenderWalk= new Animation(0.1f,frames);
        frames.clear();

        defenderStand = new TextureRegion(getTexture(),655,106,106,155);

        defineDefender();
        setBounds(0,0,60/Defenders.PPM,72/Defenders.PPM);
        setRegion(defenderStand);

        //Testing for collision
        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.PLAYER;
        b2body.setUserData(bodyUserData);
        ///////////////////////////////////////
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));



    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case WALKING:
                region = (TextureRegion) defenderWalk.getKeyFrame(stateTimer,true);
                break;
            case STANDING:
                default:
                    region = defenderStand;
                    break;
        }

        if((b2body.getLinearVelocity().x>0 || !walkingRight) && !region.isFlipX()){
            region.flip(true,false);
            walkingRight = false;
        }

        else if((b2body.getLinearVelocity().x <0 || walkingRight) && region.isFlipX()){
            region.flip(true,false);
            walkingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer +dt : 0;
        previousState = currentState;
        return region;


    }

    public State getState(){
        if(b2body.getLinearVelocity().x != 0)
            return State.WALKING;
        else
            return State.STANDING;
    }

    public void defineDefender(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(200/Defenders.PPM,240/Defenders.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 /Defenders.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("body");

        EdgeShape shield = new EdgeShape();
        shield.set(new Vector2(-25/Defenders.PPM,30/Defenders.PPM),new Vector2(25/Defenders.PPM,30/Defenders.PPM));
        fdef.shape = shield;

        b2body.createFixture(fdef).setUserData("shield");
    }

    public void turnOnPowerUp(){

    }
}
