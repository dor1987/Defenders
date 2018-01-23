package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.defenders.Screens.PlayScreen;
import com.dorashush.defenders.Tools.BodyUserData;

/**
 * Created by Dor on 01/23/18.
 */

public abstract class Ball extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    public float ballAngle;
    public float ballVelocity;

    private BodyUserData bodyUserData;

    public Ball(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineBall();
        velocity = new Vector2(0,0); //starting Speed
        ballAngle = (float) (Math.random()*-0.8*Math.PI);//stating angle
        ballVelocity = 1;

        //Testing for collision
        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.BALL;
        b2body.setUserData(bodyUserData);
        ///////////////////////////////////////
    }

    protected abstract void defineBall();
    public abstract void removeFromGame();

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
