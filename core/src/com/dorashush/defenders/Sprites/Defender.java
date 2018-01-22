package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.defenders.Defenders;

/**
 * Created by Dor on 01/22/18.
 */

public class Defender extends Sprite{
    public World world;
    public Body b2body;

    public Defender(World world){
        this.world = world;
        defineDefender();
    }

    public void defineDefender(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(300/Defenders.PPM,300/Defenders.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30 /Defenders.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
