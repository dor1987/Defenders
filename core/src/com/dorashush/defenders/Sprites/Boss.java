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

public class Boss  extends Enemy {
    public enum State {
        WALKING, FIREING, STANDING, FIREING2}

    private Animation flyAnimation,shootAnimation,shootAnimation2,standAnimation;
    private float stateTimer,shootingTimer,movmentTimer,avoidFirstHitTimer;
    private boolean walkingRight;
    private Array<TextureRegion> frames;
    private Boss.State currentState,previousState;

    public Boss(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTimer = 0;
        shootingTimer = 0;
        movmentTimer =0;
        walkingRight = true;

        getAndSetAnimations();

        stateTime = 0;
        setBounds(getX(), getY(), 92 / Defenders.PPM, 87 / Defenders.PPM);
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
                setBounds(getX(), getY(), 92 / Defenders.PPM, 87 / Defenders.PPM);

                region = (TextureRegion) flyAnimation.getKeyFrame(stateTimer, true);
                break;
            case FIREING:
                region = (TextureRegion) shootAnimation.getKeyFrame(stateTimer, true);
                setBounds(getX()-20/Defenders.PPM, getY(), 112 / Defenders.PPM, 87 / Defenders.PPM);

                break;
            case FIREING2:
                setBounds(getX()-20/Defenders.PPM, getY(), 150 / Defenders.PPM, 95 / Defenders.PPM);
                region = (TextureRegion) shootAnimation2.getKeyFrame(stateTimer, true);
                break;

            case STANDING:
                setBounds(getX(), getY(), 92 / Defenders.PPM, 87 / Defenders.PPM);
                region = (TextureRegion) standAnimation.getKeyFrame(stateTimer, true);
                break;

            default:
                region = (TextureRegion) standAnimation.getKeyFrame(stateTimer, true);
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

    public Boss.State getState() {
        if(getPhase()==1) {
            shootingTimer %=4;

            if (previousState == Boss.State.FIREING && shootingTimer > 0.02 && shootingTimer < 0.5) {
                return Boss.State.FIREING;
            } else if (shootingTimer <= 0.02 && avoidFirstHitTimer > 2) {
                return Boss.State.FIREING;
            } else {
                return State.STANDING;
            }
        }

        else if(getPhase()==2) {
            shootingTimer %=3;

            if (previousState == Boss.State.FIREING && shootingTimer > 0.02 && shootingTimer < 0.5) {
                return Boss.State.FIREING;
            } else if (shootingTimer <= 0.02 && avoidFirstHitTimer > 2) {
                return Boss.State.FIREING;
            } else {
                return State.WALKING;
            }
        }

        else{
            shootingTimer %=3;

            if (previousState == Boss.State.FIREING2 && shootingTimer > 0.02 && shootingTimer < 1.8) {
                return Boss.State.FIREING2;
            } else if (shootingTimer <= 0.02 && avoidFirstHitTimer > 2) {
                return Boss.State.FIREING2;
            } else {
                return Boss.State.WALKING;
            }
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
        shape.setRadius(15 / Defenders.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void onBallHit() {
        setHealthBar((float) (getHealthBar() - 0.1));

        if (getHealthBar() <= 0) {
            Hud.addScore(9000);
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

    public int getPhase(){
        float currentHealthBarStatus = getHealthBar();
        if(currentHealthBarStatus>=0.7)
            return 1;
        else if(currentHealthBarStatus>=0.4)
            return 2;
        else
            return 3;
    }

    public void getAndSetAnimations(){
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("bosswalk"), i * 93, 0, 93, 93));
        flyAnimation = new Animation(0.2f, frames);

        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("bosshit1"), (i * 119)+88, 0, 119, 87));
        shootAnimation = new Animation(0.2f, frames);

        frames.clear();


        frames.add(new TextureRegion(screen.getAtlas().findRegion("bosshit2"), 290, 0, 117, 95));

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("bosshit2"), i*133+406, 0, 133, 95));

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("bosshit2"), 1080+i*183, 0, 183, 95));


        shootAnimation2 = new Animation(0.2f, frames);
        frames.clear();

        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("bossstand"), i * 92, 0, 91, 87));
        standAnimation = new Animation(0.2f, frames);
    }
    public void movment(float dt){
        if (!gotHit) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt));



            if (getState() == Boss.State.FIREING) {
                b2body.setLinearVelocity(velocity2);
            }

            else if(getState() == State.STANDING){
                b2body.setLinearVelocity(velocity2);
            }

            else if(getState() == Boss.State.FIREING2){
                b2body.setLinearVelocity(velocity2);
            }

            else{
                if(movmentTimer>=0.5) {
                    velocity3.x = velocity.x * generateNumber(2);
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