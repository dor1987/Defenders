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
    private World world;
    private TiledMap map;
    private  BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;
    private Body body;

    public B2WorldCreator(PlayScreen screen){
        world = screen.getWorld();
        map = screen.getMap();
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();
        villageBodies();
        sideBoundsBodies();
        enemyBoundBodies();
    }
    public void villageBodies(){
        //village bodies
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Defenders.PPM,(rect.getY() + rect.getHeight()/2)/ Defenders.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ Defenders.PPM,(rect.getHeight())/2/ Defenders.PPM);
            fdef.shape= shape;
            body.createFixture(fdef);
            bodyUserData = new BodyUserData();
            bodyUserData.collisionType = BodyUserData.CollisionType.VILLAGE;
            body.setUserData(bodyUserData);
        }
    }
    public void sideBoundsBodies(){
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
    }
    public void enemyBoundBodies(){
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
