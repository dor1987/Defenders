package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;

import java.util.Random;

/**
 * Created by Dor on 02/19/18.
 */

public class IceDinoBall  extends Ball {
    private float stateTime;
    private Animation moveAnimation;
    private Array<TextureRegion> frames;
    private boolean setToRemove;
    // public boolean removed;
    private boolean setToHitVillage;
    //public boolean hitedTheVillage;
    private float speedChangeTimer;
    private boolean toIncreaseSpeed;
    private float RandomTime;

    public IceDinoBall(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for(int i = 0; i<3 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("icedinoball"), i *62,0,62,34));
        moveAnimation = new Animation(0.2f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),55 / Defenders.PPM,34/Defenders.PPM);
        setToRemove = false;
        removed = false;

        setToHitVillage = false;
        hitedTheVillage = false;
        speedChangeTimer =0;
        toIncreaseSpeed = true;
        RandomTime = generateNumber(4);
    }

    public void update(float dt) {
        stateTime += dt;
        speedChangeTimer +=dt;

        if(setToHitVillage && !hitedTheVillage){ //removing the body but the texture will stay
            world.destroyBody(b2body);
            hitedTheVillage = true;
            removed = true;
        }



        else if(!hitedTheVillage) {

            if (setToRemove && !removed) {
                world.destroyBody(b2body);
                removed = true;
            } else if (!removed) {
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                //  TextureRegion frame =(TextureRegion) moveAnimation.getKeyFrame(stateTime, true);
                //  frame.flip(true,false);
                setRegion((TextureRegion) moveAnimation.getKeyFrame(stateTime, true));
                //setRegion(frame);

                if (velocity.x == 0 && velocity.y == 0) {
                    velocity.x = (float) (ballVelocity * Math.cos(ballAngle));
                    velocity.y = (float) (ballVelocity * Math.sin(ballAngle));
                }
                setOriginCenter();
                setFlip(true,false);
                setRotation(velocity.angle());


                b2body.setLinearVelocity(velocity);

                if(speedChangeTimer>=RandomTime){
                    if(toIncreaseSpeed) {
                        velocity.x *= 2;
                        velocity.y *= 2;
                        toIncreaseSpeed = false;
                    }
                    else if(!toIncreaseSpeed){
                        velocity.x /= 2;
                        velocity.y /= 2;
                        toIncreaseSpeed = true;
                    }
                    RandomTime = generateNumber(4);
                    speedChangeTimer=0;
                }
            }
        }
    }

    @Override
    protected void defineBall() {
        BodyDef bdef = new BodyDef();
        // bdef.position.set(240/ Defenders.PPM,500/Defenders.PPM);//need 2 change by enemy spot
        bdef.position.set(getX(),getY()-(5/Defenders.PPM));//need 2 change by enemy spot

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 /Defenders.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void removeFromGame() {
        this.setToRemove=true;
    }

    public void hitTheVillage() {
        this.setToHitVillage=true;
    }

    public int generateNumber(int maxNum) {
        Random random = new Random();
        int result = random.nextInt(maxNum+1); //to avoid maxnum been 0

        return result;
    }
}
