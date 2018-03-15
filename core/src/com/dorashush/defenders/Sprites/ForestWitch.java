package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Scenes.Hud;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 02/19/18.
 */

public class ForestWitch extends Enemy{
    public enum State {WALKING,FIREING}
    private Animation flyAnimation,shootAnimation;
    private float stateTimer,shootingTimer,avoidFirstHitTimer;
    private boolean walkingRight;

    private Array<TextureRegion> frames;
    ForestWitch.State currentState;
    ForestWitch.State previousState;

    public ForestWitch(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTimer = 0;
        shootingTimer =0;
        walkingRight = true;
        getAndSetAnimations();
        stateTime = 0;
        setBounds(getX(),getY(),97 / Defenders.PPM,82/Defenders.PPM);
        removed = false;
        gotHit = false;
        avoidFirstHitTimer= 0;

    }

    public void update(float dt) {
        stateTime += dt;
        shootingTimer += dt;
        avoidFirstHitTimer += dt;
        movment(dt);
    }
    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case WALKING:
                region = (TextureRegion) flyAnimation.getKeyFrame(stateTimer,true);
                break;
            case FIREING:
            default:
                region = (TextureRegion) shootAnimation.getKeyFrame(stateTimer,true);
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
    public ForestWitch.State getState(){
        shootingTimer%=4;

        if(previousState == ForestWitch.State.FIREING && shootingTimer>0.02 &&shootingTimer<0.7){
            return ForestWitch.State.FIREING;
        }

        else if(shootingTimer <=0.02 && avoidFirstHitTimer>2){
            return ForestWitch.State.FIREING;
        }
        else{
            return ForestWitch.State.WALKING;
        }

    }
    public void draw(Batch batch){
        if(!removed || stateTime < 2)
            super.draw(batch);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 /Defenders.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void onBallHit() {
        setHealthBar((float)(getHealthBar()-0.15));

        if(getHealthBar() <= 0) {
            Hud.addScore(1600);
            gotHit = true;
        }
    }


    @Override
    public float getTimer() {
        return stateTime;
    }
    public void getAndSetAnimations(){
        frames = new Array<TextureRegion>();
        for(int i = 0; i<6 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("forestwitchwalk2"), i *117,0,117,93));
        flyAnimation = new Animation(0.2f,frames);

        frames.clear();

        for(int i = 0; i<6 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("forestwitchhit"), i *126,0,126,106));
        shootAnimation = new Animation(0.2f,frames);

    }
    public void movment(float dt){
        if (!gotHit) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            //setRegion((TextureRegion) flyAnimation.getKeyFrame(stateTime, true));
            setRegion(getFrame(dt));

            if(getState()==ForestWitch.State.FIREING){
                velocity3.x =velocity.x*2;
                b2body.setLinearVelocity(velocity3);
            }
            else {
                b2body.setLinearVelocity(velocity);
            }

        } else if (gotHit ) {
            if (!removed) {
                world.destroyBody(b2body);
                removed = true;
                stateTime = 0;
            }

        }
    }
}

