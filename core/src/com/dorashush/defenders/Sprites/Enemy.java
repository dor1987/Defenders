package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.defenders.Scenes.Hud;
import com.dorashush.defenders.Screens.PlayScreen;
import com.dorashush.defenders.Tools.BodyUserData;

/**
 * Created by Dor on 01/22/18.
 */

public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    private BodyUserData bodyUserData;
    public boolean removed;
    public boolean gotHit;
    public float stateTime;
    private float healthBar;
    public Vector2 velocity2; //for standing
    public Vector2 velocity3; //for sprinting


    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(1,0);
        velocity2 = new Vector2(0,0);
        velocity3 = new Vector2(0,0);
        healthBar =1;
        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.ENEMY;
        b2body.setUserData(bodyUserData);
    }

    public float getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(float healthBar) {
        this.healthBar = healthBar;
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void onBallHit();
    public abstract float getTimer();
    public void reverseVelocity(boolean x,boolean y){
        if(x)
            velocity.x= -velocity.x;
    }



}
