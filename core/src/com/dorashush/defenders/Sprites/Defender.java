package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 01/22/18.
 */

public class Defender extends Sprite{
    public World world;
    public Body b2body;
    private TextureRegion defenderStand;

    public Defender(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("playersheet"));
        this.world = world;
        defineDefender();
        defenderStand = new TextureRegion(getTexture(),2,2,106,155);
        setBounds(0,0,50/Defenders.PPM,60/Defenders.PPM);
        setRegion(defenderStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);
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
