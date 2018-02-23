package com.dorashush.defenders;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dorashush.defenders.Defenders;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AndroidLauncher extends AndroidApplication implements LeaderBoardHandler{
	public static FirebaseDatabase mFirebaseDatabase;
	public static DatabaseReference mLeaderboardReference;
	public static ScoreLine tempScoreLine;
	int score;
	boolean isHighScore;


	public static Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//Adding new score to the data base
			String playerName =((Bundle)msg.getData()).getString("name","fail");
			int playerScore = ((Bundle)msg.getData()).getInt("score",0);
			mLeaderboardReference.push().setValue(new ScoreLine(playerName,playerScore));

		}
	};


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFirebaseDatabase = FirebaseDatabase.getInstance();
		mLeaderboardReference = mFirebaseDatabase.getReference().child("leaderboard");

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Defenders(this), config);

		//to get lowest score for database
		mLeaderboardReference.orderByChild("score").limitToFirst(1).addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				tempScoreLine = dataSnapshot.getValue(ScoreLine.class);
				score = tempScoreLine.score;

			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

	}


	@Override
	public void addPlayerScoreToDataBase(String name , int score) {
		Bundle bundle = new Bundle();
		bundle.putString("name",name);
		bundle.putInt("score",score);

		Message highScore = new Message();

		highScore.setData(bundle);

		handler.sendMessage(highScore);
	}


	@Override
	public int getLowestScoreOnBoard() {
	mLeaderboardReference.orderByChild("score").limitToFirst(1).addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				ScoreLine tempScoreLine = dataSnapshot.getValue(ScoreLine.class);

				score = tempScoreLine.score;


				Log.d("","LOOK HERE FOR SCORE"+score);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		return score;

	}

	@Override
	public boolean isHighScore(final int scoreToCheck) {
		isHighScore = false;

		if(score < scoreToCheck)
			isHighScore = true;


		return isHighScore;
	}


}

  class ScoreLine{
	public String name;
	public int score;

	  public ScoreLine(){
	  }

	public ScoreLine(String name,int score){
		this.name=name;
		this.score=score;
	}

	  public String getName() {
		  return name;
	  }

	  public void setName(String name) {
		  this.name = name;
	  }

	  public int getScore() {
		  return score;
	  }

	  public void setScore(int score) {
		  this.score = score;
	  }
  }
