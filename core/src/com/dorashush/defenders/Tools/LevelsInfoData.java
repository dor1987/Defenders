package com.dorashush.defenders.Tools;

/**
 * Created by Dor on 01/26/18.
 */


public class LevelsInfoData {
    public static final int AMOUNT_OF_LEVELS = 9;
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
              enemy = 0;
              ball = 0;
              powerUps = 1;
              levelNum = 1;
              timeBetweenBalls=4;
              amountOfBallsPerShoot = 1;
              timeBetweenPowerUps = 5;

             break;

          case 1:
              enemy = 1;
              ball = 1;
              powerUps = 1;
              levelNum = 2;
              timeBetweenBalls=4;
              amountOfBallsPerShoot = 1;
              timeBetweenPowerUps = 10;

              break;

            case 2:
                enemy = 2;
                ball = 2;
                powerUps = 2;
                levelNum = 3;
                timeBetweenBalls=4;
                amountOfBallsPerShoot = 2;
                timeBetweenPowerUps = 10;

                break;

            case 3:
                enemy = 3;
                ball = 3;
                powerUps = 3;
                levelNum = 4;
                timeBetweenBalls=4;
                amountOfBallsPerShoot = 2;
                timeBetweenPowerUps = 10;

                break;

            case 4:
                enemy = 4;
                ball = 4;
                powerUps = 3;
                levelNum = 5;
                timeBetweenBalls=4;
                amountOfBallsPerShoot = 2;
                timeBetweenPowerUps = 9;

                break;

            case 5:
                enemy = 5;
                ball = 5;
                powerUps = 2;
                levelNum = 6;
                timeBetweenBalls=7;
                amountOfBallsPerShoot = 3;
                timeBetweenPowerUps = 8;
                break;

            case 6:
                enemy = 6;
                ball = 6;
                powerUps = 3;
                levelNum = 7;
                timeBetweenBalls=7;
                amountOfBallsPerShoot = 1;
                timeBetweenPowerUps = 9;
                break;

            case 7:
                enemy = 7;
                ball = 7;
                powerUps = 3;
                levelNum = 8;
                timeBetweenBalls=8;
                amountOfBallsPerShoot = 1;
                timeBetweenPowerUps = 7;
                break;

            case 666:
                enemy = 666;
                ball = 666;
                powerUps = 3;
                levelNum = 666;
                timeBetweenBalls=4;
                amountOfBallsPerShoot = 1;
                timeBetweenPowerUps = 5;
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
