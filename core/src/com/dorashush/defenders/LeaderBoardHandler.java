package com.dorashush.defenders;

import java.util.ArrayList;

/**
 * Created by Dor on 02/22/18.
 */

public interface LeaderBoardHandler {
    public void addPlayerScoreToDataBase(String name , int score);
    public boolean isHighScore(int scoreToCheck);
    public ArrayList<String> getTopSeven();
}
