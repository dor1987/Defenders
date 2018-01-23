package com.dorashush.defenders.Tools;

/**
 * Created by Dor on 01/23/18.
 */

public class BodyUserData {
    public enum CollisionType {
        UNKNOWN,
        WALL,
        BALL,
        PLAYER,
        ENEMY,
        VILLAGE

    }

    public CollisionType collisionType = CollisionType.UNKNOWN;
}
