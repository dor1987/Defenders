package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.defenders.Screens.PlayScreen;
import com.dorashush.defenders.Tools.BodyUserData;

/**
 * Created by Dor on 01/25/18.
 */

public abstract class PowerUp extends Sprite{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    public float powerUpAngle;
    public float powerUpVelocity;
    public boolean removed;

    private BodyUserData bodyUserData;





    public PowerUp(PlayScreen screen){
        this.world = screen.getWorld();
        this.screen = screen;
        definePowerUp();
        velocity = new Vector2(0,0); //starting Speed
        powerUpAngle = (float) (Math.random()*-0.6*Math.PI);//stating angle
        powerUpVelocity = 2;

        //Testing for collision
        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.POWER_UP;
        b2body.setUserData(bodyUserData);
        ///////////////////////////////////////
    }

    public abstract void update(float dt);
    protected abstract void definePowerUp();
    public abstract void setToRemove();
    public void reverseVelocity(boolean x,boolean y){
        Gdx.app.log("Inside reverseVelocity","");

        if(x) {
            //ballAngle*=-0.5;
            velocity.x = -velocity.x;
        }
        if(y) {
            //ballAngle*=-0.5;
            velocity.y = -velocity.y;
        }
    }

}
