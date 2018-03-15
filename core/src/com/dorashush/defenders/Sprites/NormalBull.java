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

import java.util.Random;

/**
 * Created by Dor on 02/19/18.
 */

public class NormalBull  extends Enemy {
    public enum State {
        WALKING, FIREING}

    private Animation flyAnimation,shootAnimation;
    private float stateTimer,shootingTimer,movmentTimer,avoidFirstHitTimer;
    private boolean walkingRight;
    private Array<TextureRegion> frames;
    private NormalBull.State currentState,previousState;
    public NormalBull(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTimer = 0;
        shootingTimer = 0;
        movmentTimer =0;
        walkingRight = true;
        getAndSetAnimations();
        stateTime = 0;
        setBounds(getX(), getY(), 97 / Defenders.PPM, 82 / Defenders.PPM);
        removed = false;
        gotHit = false;
        avoidFirstHitTimer = 0;
    }
    public void update(float dt) {
        stateTime += dt;
        shootingTimer += dt;
        avoidFirstHitTimer += dt;
        movmentTimer +=dt;
        movment(dt);
    }
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case WALKING:
                region = (TextureRegion) flyAnimation.getKeyFrame(stateTimer, true);
                break;
            case FIREING:
            default:
                region = (TextureRegion) shootAnimation.getKeyFrame(stateTimer, true);
                break;
        }

        if ((b2body.getLinearVelocity().x > 0 || !walkingRight) && !region.isFlipX()) {
            region.flip(true, false);
            walkingRight = false;
        } else if ((b2body.getLinearVelocity().x < 0 || walkingRight) && region.isFlipX()) {
            region.flip(true, false);
            walkingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    public NormalBull.State getState() {
        shootingTimer %= 7;
        if (previousState == NormalBull.State.FIREING && shootingTimer > 0.02 && shootingTimer < 1.1) {
            return NormalBull.State.FIREING;
        } else if (shootingTimer <= 0.02 && avoidFirstHitTimer > 2) {
            return NormalBull.State.FIREING;
        } else {
            return NormalBull.State.WALKING;
        }
    }
    public void draw(Batch batch) {
        if (!removed || stateTime < 2)
            super.draw(batch);
    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / Defenders.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void onBallHit() {
        setHealthBar((float) (getHealthBar() - 0.15));
        if (getHealthBar() <= 0) {
            Hud.addScore(2200);
            gotHit = true;
        }
    }


    @Override
    public float getTimer() {
        return stateTime;
    }

    public int generateNumber(int maxNum) {
        Random random = new Random();
        int result = random.nextInt(maxNum+1); //to avoid maxnum been 0
        int plusOrMinus = random.nextInt(1);

        if(plusOrMinus == 0)
            result*=-1;
        return result;
    }
    public void getAndSetAnimations(){
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("normalbullwalk"), i * 140, 0, 140, 105));
        flyAnimation = new Animation(0.2f, frames);

        frames.clear();

        for (int i = 0; i < 10; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("normalbullhit"), i * 150, 0, 150, 122));
        shootAnimation = new Animation(0.2f, frames);
    }
    public void movment(float dt){
        if (!gotHit) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            //setRegion((TextureRegion) flyAnimation.getKeyFrame(stateTime, true));
            setRegion(getFrame(dt));

            if (getState() == NormalBull.State.FIREING) {
                b2body.setLinearVelocity(velocity2);
            } else {
                if(getX() >= 480/Defenders.PPM){
                    velocity3.x= -3;
                }
                else if(getX() <= 0){
                    velocity3.x= 3;
                }

                if(movmentTimer>=0.9) {
                    velocity3.x = velocity.x * generateNumber(3);
                    movmentTimer=0;
                }
                b2body.setLinearVelocity(velocity3);
            }

        } else if (gotHit) {
            if (!removed) {
                world.destroyBody(b2body);
                removed = true;
                stateTime = 0;
            }

        }
    }
}