package com.dorashush.defenders.Tools;

/**
 * Created by Dor on 01/26/18.
 */


public class LevelsInfoData {
    public static final int AMOUNT_OF_LEVELS = 2;
    private int enemy;
    private int ball;
    private int powerUps;
    private int levelNum; // the number that will be displayed on hud
    private int[] levelInfo;

    public int[] getCurrentLevelInfo(int levelNumber){
        switch (levelNumber){
          case 0:
              enemy = 0;
              ball = 0;
              powerUps = 0;
              levelNum = 1;

             break;

          case 1:
              //implment level 2
              enemy = 0;
              ball = 0;
              powerUps = 0;
              levelNum = 2;
             break;

          default:
              enemy = 0;
              ball = 0;
              powerUps = 0;
              break;
        }

      levelInfo = new int[]{enemy,ball,powerUps,levelNum,AMOUNT_OF_LEVELS};
        return levelInfo;
    }
}
