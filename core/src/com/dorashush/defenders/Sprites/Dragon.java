package com.dorashush.defenders.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Scenes.Hud;
import com.dorashush.defenders.Screens.PlayScreen;

/**
 * Created by Dor on 01/22/18.
 */

    public class Dragon extends Enemy {
    private Animation flyAnimation;
    private Array<TextureRegion> frames;

    public Dragon(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        getAndSetAnimations();
        stateTime = 0;
        setBounds(getX(),getY(),128 /Defenders.PPM,143/Defenders.PPM);
        removed = false;
        gotHit = false;

    }
    public void update(float dt) {
        stateTime += dt;

        if (!gotHit) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) flyAnimation.getKeyFrame(stateTime, true));
            b2body.setLinearVelocity(velocity);

        } else if (gotHit ) {
            if (!removed) {
                world.destroyBody(b2body);
                removed = true;
                stateTime = 0;
            }
        }
    }
    public void draw(Batch batch){
    if(!removed || stateTime < 2)
       super.draw(batch);
}
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30 /Defenders.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void onBallHit() {
        setHealthBar((float)(getHealthBar()-0.25));
        if(getHealthBar() <= 0) {
            Hud.addScore(800);
            gotHit = true;
        }
    }

    @Override
    public float getTimer() {
        return stateTime;
    }

    public void getAndSetAnimations(){
        frames = new Array<TextureRegion>();
        for(int i = 0; i<4 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("firedragonwalk"), i *128,0,128,143));
        flyAnimation = new Animation(0.2f,frames);
    }

}
