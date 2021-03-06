package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Scenes.Controller;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 02/19/18.
 */

public class LightingPowerUp extends PowerUp{
    private float stateTime;
    private Animation moveAnimation;
    private Array<TextureRegion> frames;
    private boolean setToRemove;
    //private boolean setGotCollected;

    public LightingPowerUp(PlayScreen screen) {
        super(screen);
        frames = new Array<TextureRegion>();
        for(int i = 0; i<9 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("speedcoin"), i *48,0,48,48));
        moveAnimation = new Animation(0.2f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),40 / Defenders.PPM,40/Defenders.PPM);
        setToRemove = false;
        removed = false;

        setToRemove = false;
        removed = false;
    }


    public void update(float dt) {
        stateTime += dt;
        if(setToRemove && !removed){ //removing the body but the texture will stay
            world.destroyBody(b2body);
            removed = true;
        }

        if (setToRemove && !removed) {
            world.destroyBody(b2body);
            removed = true;
        }

        else if (!removed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) moveAnimation.getKeyFrame(stateTime, true));

            if (velocity.x == 0 && velocity.y == 0) {
                velocity.x = (float) (powerUpVelocity * Math.cos(powerUpAngle));
                velocity.y = (float) (powerUpVelocity * Math.sin(powerUpAngle));
            }
            //setOriginCenter();
            //setRotation(velocity.angle());
            b2body.setLinearVelocity(velocity);
        }

    }

    @Override
    protected void definePowerUp() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(240/ Defenders.PPM,500/Defenders.PPM);//need 2 change to randomly generate at the center
        //bdef.position.set(getX(),getY());

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 /Defenders.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void setToRemove() {
        this.setToRemove = true;
    }

    @Override
    public void onPlayerCaught() {
        Controller.setAmountOfSpeeds(Controller.getAmountOfSpeeds()+1);

    }

}

