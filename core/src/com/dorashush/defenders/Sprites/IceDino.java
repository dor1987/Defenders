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

public class IceDino extends Enemy {
    public enum State {
        WALKING, FIREING
    }
    private Animation flyAnimation,shootAnimation;
    private float stateTimer,shootingTimer,movmentTimer,avoidFirstHitTimer;
    private boolean walkingRight;
    private Array<TextureRegion> frames;
    IceDino.State currentState,previousState;

    public IceDino(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTimer = 0;
        shootingTimer = 0;
        movmentTimer =0;
        walkingRight = true;
        getAndSetAnimations();
        stateTime = 0;
        setBounds(getX(), getY(), 103 / Defenders.PPM, 83 / Defenders.PPM);
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
    public IceDino.State getState() {
        shootingTimer %= 6;

        if (previousState == IceDino.State.FIREING && shootingTimer > 0.02 && shootingTimer < 0.9) {
            return IceDino.State.FIREING;
        } else if (shootingTimer <= 0.02 && avoidFirstHitTimer > 2) {
            return IceDino.State.FIREING;
        } else {
            return IceDino.State.WALKING;
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
        shape.setRadius(25 / Defenders.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void onBallHit() {
        setHealthBar((float) (getHealthBar() - 0.15));

        if (getHealthBar() <= 0) {
            Hud.addScore(2500);
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
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("icedinowalk"), i * 144, 0, 144, 87));
        flyAnimation = new Animation(0.2f, frames);

        frames.clear();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("icedinohit"), i * 167, 0, 167, 133));
        shootAnimation = new Animation(0.2f, frames);
    }
    public void movment(float dt){
        if (!gotHit) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt));

            if (getState() == IceDino.State.FIREING) {
                b2body.setLinearVelocity(velocity2);
            } else {
                b2body.setLinearVelocity(velocity);
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
