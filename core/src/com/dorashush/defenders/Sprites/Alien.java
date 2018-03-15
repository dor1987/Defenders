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

public class Alien extends Enemy{
    public enum State {WALKING,FIREING};
    private Animation flyAnimation,shootAnimation;
    private float stateTimer,shootingTimer,avoidFirstHitTimer;
    private boolean walkingRight;
    private Array<TextureRegion> frames;
    private Alien.State currentState,previousState;

    public Alien(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTimer = 0;
        shootingTimer =0;
        walkingRight = true;
        getAndSetAnimations();


        stateTime = 0;
        setBounds(getX(),getY(),94 / Defenders.PPM,105/Defenders.PPM);
        removed = false;
        gotHit = false;
        avoidFirstHitTimer= 0;
    }
    public void update(float dt) {
        stateTime += dt;
        shootingTimer += dt;
        avoidFirstHitTimer += dt;

        if (!gotHit) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt));
            if(getState()==Alien.State.FIREING){
                b2body.setLinearVelocity(velocity2);
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
    public Alien.State getState(){
        shootingTimer%=4;

        if(previousState == Alien.State.FIREING && shootingTimer>0.02 &&shootingTimer<0.6){
            return Alien.State.FIREING;
        }

        else if(shootingTimer <=0.02 && avoidFirstHitTimer>2){
            return Alien.State.FIREING;
        }
        else{
            return Alien.State.WALKING;
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

    public void getAndSetAnimations(){
        frames = new Array<TextureRegion>();
        for(int i = 0; i<4 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("allienwalk"), i *106,0,106,103));

        flyAnimation = new Animation(0.2f,frames);
        frames.clear();
        for(int i = 0; i<5 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("allienhit"), i *93,0,93,105));
        shootAnimation = new Animation(0.2f,frames);
    }

    @Override
    public void onBallHit() {
        setHealthBar((float)(getHealthBar()-0.2));

        if(getHealthBar() <= 0) {
            Hud.addScore(1200);
            gotHit = true;
        }
    }

    @Override
    public float getTimer() {
        return stateTime;
    }

}

