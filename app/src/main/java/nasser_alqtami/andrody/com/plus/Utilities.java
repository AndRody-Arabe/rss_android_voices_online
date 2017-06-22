package nasser_alqtami.andrody.com.plus;


import android.app.Activity;
import android.util.DisplayMetrics;

public class Utilities {
	private static Utilities utils;

	public Utilities() {}

	public String milliSecondsToTimer(long milliseconds){
		String finalTimerString = "";
		String secondsString = "";
		
		   int hours = (int)( milliseconds / (1000*60*60));
		   int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
		   int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
		   if(hours > 0){
			   finalTimerString = hours + ":";
		   }
		   
		   if(seconds < 10){
			   secondsString = "0" + seconds;
		   }else{
			   secondsString = "" + seconds;}
		   
		   finalTimerString = finalTimerString + minutes + ":" + secondsString;
		
		return finalTimerString;
	}

	public int getProgressPercentage(long currentDuration, long totalDuration){
		Double percentage = (double) 0;
		
		long currentSeconds = (int) (currentDuration / 1000);
		long totalSeconds = (int) (totalDuration / 1000);
		
		percentage =(((double)currentSeconds)/totalSeconds)*100;
		
		return percentage.intValue();
	}

	public int progressToTimer(int progress, int totalDuration) {
		int currentDuration = 0;
		totalDuration = (int) (totalDuration / 1000);
		currentDuration = (int) ((((double)progress) / 100) * totalDuration);
		
		return currentDuration * 1000;
	}

	public static Utilities getInstance(){
		if(utils==null){
			utils=new Utilities();
		}
		return utils;
	}

	public int getNoOfColumns(Activity c){
		DisplayMetrics metrics = new DisplayMetrics();
		c.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return 1;
	}
}
