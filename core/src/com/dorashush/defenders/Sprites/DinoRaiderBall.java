package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 02/19/18.
 */

public class DinoRaiderBall extends Ball{
    private float stateTime;
    private Animation moveAnimation;
    private Array<TextureRegion> frames;
    private boolean setToRemove;
    // public boolean removed;
    private boolean setToHitVillage;
    //public boolean hitedTheVillage;

    public DinoRaiderBall(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for(int i = 0; i<4 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("dinoraiderball"), i *108,0,108,49));
        moveAnimation = new Animation(0.2f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),108 / Defenders.PPM,49/Defenders.PPM);
        setToRemove = false;
        removed = false;

        setToHitVillage = false;
        hitedTheVillage = false;

    }

    public void update(float dt) {
        stateTime += dt;

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
            }
        }
    }

    @Override
    protected void defineBall() {
        BodyDef bdef = new BodyDef();
        // bdef.position.set(240/ Defenders.PPM,500/Defenders.PPM);//need 2 change by enemy spot
        bdef.position.set(getX(),getY());//need 2 change by enemy spot

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(14 /Defenders.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void removeFromGame() {
        this.setToRemove=true;
    }

    public void hitTheVillage() {
        this.setToHitVillage=true;
    }

}
