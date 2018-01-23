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
 * Created by Dor on 01/23/18.
 */

public class SimpleBall extends Ball {

    private float stateTime;
    private Animation moveAnimation;
    private Array<TextureRegion> frames;

    public SimpleBall(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for(int i = 0; i<11 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("balls"), i *48,28,48,48));
        moveAnimation = new Animation(0.2f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),48 / Defenders.PPM,48/Defenders.PPM);
    }

    public void update(float dt){
        stateTime+=dt;
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);
        setRegion((TextureRegion) moveAnimation.getKeyFrame(stateTime,true));

        b2body.setLinearVelocity(velocity);

    }

    @Override
    protected void defineBall() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(240/ Defenders.PPM,500/Defenders.PPM);//need 2 change by enemy spot
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(24 /Defenders.PPM);
        fdef.shape = shape;
    }

}
