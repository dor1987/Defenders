package com.dorashush.defenders.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dorashush.defenders.Sprites.Enemy;

/**
 * Created by Dor on 01/22/18.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.BALL)) {
            if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.VILLAGE)) {
                //TO-DO Reduce Player Live if no Life End game method

            } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.PLAYER)) {
                //BALL HIT PLAYER
                // ballHit(fixtureA.getBody());
            } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.ENEMY)) {
                //Ball hit Enemy ,Destory Enemey
            }


        } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.BALL)) {
            if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.VILLAGE)) {
                //TO-DO Reduce Player Live if no Life End game method

            } else if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.PLAYER)) {
                //BALL HIT PLAYER
                // ballHit(fixtureA.getBody());            }
            } else if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.ENEMY)) {
                //Ball hit Enemy ,Destory Enemey
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean fixtureIsCollisionType(Fixture fixture,  BodyUserData.CollisionType collisionType) {
        Body body = fixture.getBody();
        if (body != null) {
            BodyUserData bodyUserData = (BodyUserData)body.getUserData();
            if (bodyUserData != null) {
                return (bodyUserData.collisionType == collisionType);
            }
        }
        return false;
    }
}
