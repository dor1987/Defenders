package com.dorashush.defenders.Tools;

/**
 * Created by Dor on 01/26/18.
 */


public class LevelsInfoData {
    public static final int AMOUNT_OF_LEVELS = 5;
    private int enemy;
    private int ball;
    private int powerUps;
    private int timeBetweenBalls;
    private int amountOfBallsPerShoot;
    private int timeBetweenPowerUps;

    private int levelNum; // the number that will be displayed on hud
    private int[] levelInfo;

    public int[] getCurrentLevelInfo(int levelNumber){
        switch (levelNumber){
          case 0:
              enemy = 4;
              ball = 4;
              powerUps = 1;
              levelNum = 1;
              timeBetweenBalls=4;
              amountOfBallsPerShoot = 1;
              timeBetweenPowerUps = 10;
             break;

          case 1:
              //implment level 2
              enemy = 1;
              ball = 1;
              powerUps = 1;
              levelNum = 2;
              timeBetweenBalls=4;
              amountOfBallsPerShoot = 1;
              timeBetweenPowerUps = 10;

              break;

            case 2:
                //implment level 2
                enemy = 2;
                ball = 2;
                powerUps = 1;
                levelNum = 2;
                timeBetweenBalls=4;
                amountOfBallsPerShoot = 2;
                timeBetweenPowerUps = 10;

                break;

            case 3:
                //implment level 2
                enemy = 3;
                ball = 3;
                powerUps = 1;
                levelNum = 2;
                timeBetweenBalls=4;
                amountOfBallsPerShoot = 2;
                timeBetweenPowerUps = 10;

                break;

            case 4:
                //implment level 2
                enemy = 4;
                ball = 4;
                powerUps = 1;
                levelNum = 2;
                timeBetweenBalls=4;
                amountOfBallsPerShoot = 2;
                timeBetweenPowerUps = 10;

                break;

          default:
              enemy = 0;
              ball = 0;
              powerUps = 0;
              break;
        }

      levelInfo = new int[]{enemy,ball,powerUps,levelNum,AMOUNT_OF_LEVELS,timeBetweenBalls,amountOfBallsPerShoot,timeBetweenPowerUps};
        return levelInfo;
    }
}
