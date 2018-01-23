package com.dorashush.defenders.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dorashush.defenders.Sprites.Enemy;
import com.dorashush.defenders.Sprites.SimpleBall;

/**
 * Created by Dor on 01/22/18.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.BALL)) {
            Gdx.app.log("Start Contact with BALL","");

            if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.VILLAGE)) {
                //TO-DO Reduce Player Live if no Life End game method

            } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.PLAYER)) {
                //BALL HIT PLAYER
                // ballHit(fixtureA.getBody());
            } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.ENEMY)) {
                //Ball hit Enemy ,Destory Enemey
            }
            else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.WALL)) {
                Gdx.app.log("Ball Touched wall","");

                Vector2 currentVelocity = fixtureB.getBody().getLinearVelocity();
                currentVelocity.x *=-1;
                fixtureB.getBody().setLinearVelocity(currentVelocity);
            }



        } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.BALL)) {
            Gdx.app.log("Start Contact with BALL","");

            if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.VILLAGE)) {
                //TO-DO Reduce Player Live if no Life End game method

            } else if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.PLAYER)) {
                //BALL HIT PLAYER
                // ballHit(fixtureA.getBody());            }
            } else if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.ENEMY)) {
                //Ball hit Enemy ,Destory Enemey
            }

            else if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.WALL)) {
                Gdx.app.log("Ball Touched wall","");

                Vector2 currentVelocity = fixtureA.getBody().getLinearVelocity();
                currentVelocity.x *=-1;
                fixtureB.getBody().setLinearVelocity(currentVelocity);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

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
