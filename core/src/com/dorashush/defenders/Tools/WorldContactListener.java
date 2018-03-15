package com.dorashush.defenders.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dorashush.defenders.Sprites.Ball;
import com.dorashush.defenders.Sprites.Defender;
import com.dorashush.defenders.Sprites.Enemy;
import com.dorashush.defenders.Sprites.PowerUp;
import com.dorashush.defenders.Sprites.SimpleBall;

import static com.dorashush.defenders.Screens.PlayScreen.godMode;

/**
 * Created by Dor on 01/22/18.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        ballMovment(fixtureA,fixtureB);
        enemyMovment(fixtureA,fixtureB);
        powerUpMovment(fixtureA,fixtureB);

    }
    public void powerUpMovment(Fixture fixtureA ,Fixture fixtureB){
        //Power up movement
        if(fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.POWER_UP)) {
            if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.WALL)) {
                //if hit wall change direction
                ((PowerUp)fixtureA.getUserData()).reverseVelocity(true,false);
            }
            else if(fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.PLAYER)) {
                //if hit player
                ((PowerUp)fixtureA.getUserData()).onPlayerCaught();
                ((PowerUp)fixtureA.getUserData()).setToRemove();
            }

            else if(fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.VILLAGE)) {
                //if hit VILLAGE
                ((PowerUp)fixtureA.getUserData()).setToRemove();
            }
        }
        else if(fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.POWER_UP)) {
            if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.WALL)) {
                //if hit wall change direction
                ((PowerUp)fixtureB.getUserData()).reverseVelocity(true,false);
            }
            else if(fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.PLAYER)) {
                //if hit player
                ((PowerUp)fixtureB.getUserData()).onPlayerCaught();
                ((PowerUp)fixtureB.getUserData()).setToRemove();
            }

            else if(fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.VILLAGE)) {
                //if hit VILLAGE
                ((PowerUp)fixtureB.getUserData()).setToRemove();
            }
        }
    }
    public void enemyMovment(Fixture fixtureA ,Fixture fixtureB){
        if(fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.ENEMY)){
            if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.WALL)) {
                Gdx.app.log("","enemy touched wall");

                ((Enemy)fixtureA.getUserData()).reverseVelocity(true,false);
            }
        }

        else if(fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.ENEMY)){

            if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.WALL)) {
                Gdx.app.log("","enemy touched wall");

                ((Enemy)fixtureB.getUserData()).reverseVelocity(true,false);

            }
        }
    }
    public void ballMovment(Fixture fixtureA ,Fixture fixtureB){
        //Ball movment
        if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.BALL)) {
            if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.VILLAGE)) {
                if(!godMode) {
                    ((Ball) fixtureA.getUserData()).hitTheVillage();
                }
                ((Ball)fixtureA.getUserData()).removeFromGame();

            } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.PLAYER)) {
                //BALL HIT PLAYER
                if(fixtureB.getShape() instanceof EdgeShape){
                    ((Ball)fixtureA.getUserData()).reverseVelocity(false,true);
                    ((Defender)fixtureB.getUserData()).onBallBlock();
                }
                else if(fixtureB.getShape() instanceof CircleShape){
                    if(!godMode) {
                        ((Ball) fixtureA.getUserData()).hitTheVillage();
                    }
                    ((Ball)fixtureA.getUserData()).removeFromGame();
                }

            } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.ENEMY)) {
                ((Ball)fixtureA.getUserData()).removeFromGame();
                ((Enemy)fixtureB.getUserData()).onBallHit();
                //Ball hit Enemy ,Destory Enemey
            }
            else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.WALL)) {
                ((Ball)fixtureA.getUserData()).reverseVelocity(true,false);
            }
            else if(fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.ENEMY_BOUNDARIES)){
                ((Ball)fixtureA.getUserData()).removeFromGame();
            }

            else if(fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.BALL)){
                if(fixtureA.getBody()!=null && fixtureB.getBody()!=null) {
                    //Ball hit ball movment
                    Vector2 velocityA = (fixtureA.getBody().getLinearVelocity());
                    Vector2 velocityB = (fixtureB.getBody().getLinearVelocity());
                    Boolean reverseX = false;
                    Boolean reverseY = false;

                    if(velocityA.x < 0  && velocityB.x > 0 || velocityA.x > 0  && velocityB.x < 0){
                        reverseX = true;
                    }

                    if(velocityA.y < 0  && velocityB.y > 0 || velocityA.y > 0  && velocityB.y < 0){
                        reverseY = true;
                    }
                    ((Ball) fixtureA.getUserData()).reverseVelocity(reverseX, reverseY);
                    ((Ball) fixtureB.getUserData()).reverseVelocity(reverseX, reverseY);
                }
            }

        } else if (fixtureIsCollisionType(fixtureB, BodyUserData.CollisionType.BALL)) {
            if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.VILLAGE)) {
                if(!godMode) {
                    ((Ball) fixtureB.getUserData()).hitTheVillage();
                }
                ((Ball)fixtureB.getUserData()).removeFromGame();

            } else if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.PLAYER)) {
                //BALL HIT PLAYER
                if(fixtureA.getShape() instanceof EdgeShape){
                    ((Defender)fixtureA.getUserData()).onBallBlock();
                    ((Ball)fixtureB.getUserData()).reverseVelocity(false,true);
                }
                else if(fixtureA.getShape() instanceof CircleShape){
                    if(!godMode) {
                        ((Ball) fixtureB.getUserData()).hitTheVillage();
                    }
                    ((Ball)fixtureB.getUserData()).removeFromGame();
                }


            } else if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.ENEMY)) {
                //Ball hit Enemy ,Destory Enemey
                ((Ball)fixtureB.getUserData()).removeFromGame();
                ((Enemy)fixtureA.getUserData()).onBallHit();
            }
            else if (fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.WALL)) {
                ((Ball)fixtureB.getUserData()).reverseVelocity(true,false);
            }

            else if(fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.ENEMY_BOUNDARIES)){
                ((Ball)fixtureB.getUserData()).removeFromGame();
            }
            else if(fixtureIsCollisionType(fixtureA, BodyUserData.CollisionType.BALL)){
                //Ball hit ball movment
                if(fixtureA.getBody()!=null && fixtureB.getBody()!=null) {
                    Vector2 velocityA = (fixtureA.getBody().getLinearVelocity());
                    Vector2 velocityB = (fixtureB.getBody().getLinearVelocity());
                    Boolean reverseX = false;
                    Boolean reverseY = false;

                    if(velocityA.x < 0  && velocityB.x > 0 || velocityA.x > 0  && velocityB.x < 0){
                        Gdx.app.log("ReverseX is true","");
                        reverseX = true;
                    }

                    if(velocityA.y < 0  && velocityB.y > 0 || velocityA.y > 0  && velocityB.y < 0){
                        reverseY = true;
                    }

                    ((Ball) fixtureA.getUserData()).reverseVelocity(reverseX, reverseY);
                    ((Ball) fixtureB.getUserData()).reverseVelocity(reverseX, reverseY);
                }
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
