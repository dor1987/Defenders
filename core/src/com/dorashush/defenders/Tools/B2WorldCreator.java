package com.dorashush.defenders.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 01/22/18.
 */

public class B2WorldCreator {
    private BodyUserData bodyUserData;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map =screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //village bodies
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Defenders.PPM,(rect.getY() + rect.getHeight()/2)/ Defenders.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Defenders.PPM,rect.getHeight()/2/ Defenders.PPM);

            fdef.shape= shape;
            body.createFixture(fdef);

            bodyUserData = new BodyUserData();
            bodyUserData.collisionType = BodyUserData.CollisionType.VILLAGE;
            body.setUserData(bodyUserData);
        }

        //side bounds bodies
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Defenders.PPM,(rect.getY() + rect.getHeight()/2)/ Defenders.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Defenders.PPM,rect.getHeight()/2/ Defenders.PPM);

            fdef.shape= shape;
            body.createFixture(fdef);

            bodyUserData = new BodyUserData();
            bodyUserData.collisionType = BodyUserData.CollisionType.WALL;
            body.setUserData(bodyUserData);
        }

/*
        //Barrier bodies
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Defenders.PPM,(rect.getY() + rect.getHeight()/2)/ Defenders.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Defenders.PPM,rect.getHeight()/2/ Defenders.PPM);

            fdef.shape= shape;
            body.createFixture(fdef);

            bodyUserData = new BodyUserData();
            bodyUserData.collisionType = BodyUserData.CollisionType.WALL;
            body.setUserData(bodyUserData);
        }
*/
        //enemy bound bodies
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Defenders.PPM,(rect.getY() + rect.getHeight()/2)/ Defenders.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Defenders.PPM,rect.getHeight()/2/ Defenders.PPM);

            fdef.shape= shape;
            body.createFixture(fdef);

            bodyUserData = new BodyUserData();
            bodyUserData.collisionType = BodyUserData.CollisionType.ENEMY_BOUNDARIES;
            body.setUserData(bodyUserData);

        }

    }

}
