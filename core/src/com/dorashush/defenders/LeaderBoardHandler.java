package com.dorashush.defenders;

/**
 * Created by Dor on 02/22/18.
 */

public interface LeaderBoardHandler {
    public void addPlayerScoreToDataBase(String name , int score);
    public int getLowestScoreOnBoard();
    public boolean isHighScore(int scoreToCheck);
}
