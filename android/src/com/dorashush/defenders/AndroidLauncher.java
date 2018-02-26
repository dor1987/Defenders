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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AndroidLauncher extends AndroidApplication implements LeaderBoardHandler{
	public static FirebaseDatabase mFirebaseDatabase;
	public static DatabaseReference mLeaderboardReference;
	public static ScoreLine tempScoreLine;
	static DatabaseReference mLowestScoreRef;

	int lowestScore;
	boolean isHighScore;
	private ArrayList<String> topSevenArray;
	private ArrayList<ScoreLine> topSevenArrayScoreLine;


	public static Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//Adding new score to the data base
			String playerName =((Bundle)msg.getData()).getString("name","fail");
			int playerScore = ((Bundle)msg.getData()).getInt("score",0);

			//remove the lowest score
			if(mLowestScoreRef!=null) {
				mLowestScoreRef.removeValue();
			}
			//add The new high score
			mLeaderboardReference.push().setValue(new ScoreLine(playerName,playerScore));
		}
	};


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFirebaseDatabase = FirebaseDatabase.getInstance();
		mLeaderboardReference = mFirebaseDatabase.getReference().child("leaderboard");
		topSevenArray = new ArrayList<String>();
		topSevenArrayScoreLine = new ArrayList<ScoreLine>();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Defenders(this), config);

		//to get lowest score for database

		mLeaderboardReference.orderByChild("score").limitToFirst(1).addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				tempScoreLine = dataSnapshot.getValue(ScoreLine.class);
				lowestScore = tempScoreLine.score;
				mLowestScoreRef = dataSnapshot.getRef();

			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				tempScoreLine = dataSnapshot.getValue(ScoreLine.class);
				lowestScore = tempScoreLine.score;
				mLowestScoreRef = dataSnapshot.getRef();
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		mLeaderboardReference.orderByChild("score").limitToLast(7).addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				String tempString;
				topSevenArrayScoreLine.add(dataSnapshot.getValue(ScoreLine.class));

			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
			topSevenArray.clear();

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
	public boolean isHighScore(final int scoreToCheck) {
		isHighScore = false;

		if(lowestScore < scoreToCheck)
			isHighScore = true;


		return isHighScore;
	}

	@Override
	public ArrayList<String> getTopSeven() {

		Collections.sort(topSevenArrayScoreLine, new ScoreLine.CustomComparator());

		for (ScoreLine sl : topSevenArrayScoreLine){
			topSevenArray.add(sl.toString());
		}


		if(topSevenArray.size()<7) {

			for(int i = topSevenArray.size() ; i<7;i++)
			topSevenArray.add("No internet connection");
		}

		return topSevenArray;
	}

	public void refresh(){
		mLeaderboardReference.orderByChild("score").limitToLast(7).addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				String tempString;
				topSevenArrayScoreLine.add(dataSnapshot.getValue(ScoreLine.class));

			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				topSevenArray.clear();

			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

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

	  @Override
	  public String toString() {
		  return "Name: " + name  + "        Score: " + score ;
	  }

	  public static class CustomComparator implements Comparator<ScoreLine> {


		  @Override
		  public int compare(ScoreLine o1, ScoreLine o2) {

			  return -1*Integer.compare(o1.score,o2.score);
		  }
	  }
  }
