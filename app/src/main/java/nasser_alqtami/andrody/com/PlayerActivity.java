package nasser_alqtami.andrody.com;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import nasser_alqtami.andrody.com.plus.FeedMusic;
import nasser_alqtami.andrody.com.plus.Utilities;

/**
 * Created by Abboudi_Aliwi on 23.06.2017.
 * Website : http://andrody.com/
 * our channel on YouTube : https://www.youtube.com/c/Andrody2015
 * our page on Facebook : https://www.facebook.com/andrody2015/
 * our group on Facebook : https://www.facebook.com/groups/Programming.Android.apps/
 * our group on Whatsapp : https://chat.whatsapp.com/56JaImwTTMnCbQL6raHh7A
 * our group on Telegram : https://t.me/joinchat/AAAAAAm387zgezDhwkbuOA
 */

public class PlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,OnCompletionListener, MediaPlayer.OnErrorListener {

    private SeekBar seek_bar;
    private ImageButton button_Play, button_Next, button_Previous;
    private ImageView image_Rhythm;
    private TextView txt_Status, current_time, sound_duration,mTitleTextView;
    private MediaPlayer media_voice;
    private AnimationDrawable mAnimation;
    private Handler mHandler = new Handler();
    private Utilities utils;

    private int currentSongIndex = 0;
    private int SELECTED_POSITION=-1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        SELECTED_POSITION=getIntent().getIntExtra("position",-1);

        processing_actionBar();
        linking_elements();

        media_voice = new MediaPlayer();
        utils = new Utilities();

        media_voice.setOnCompletionListener(this);
        media_voice.setOnErrorListener(this);
        seek_bar.setOnSeekBarChangeListener(this);
        seek_bar.setMax(media_voice.getDuration());


        currentSongIndex=SELECTED_POSITION;
        if(SELECTED_POSITION!=-1){
            playSong(SELECTED_POSITION);
        }else{
            Toast.makeText(getBaseContext(), "Error-UnknownPosition",Toast.LENGTH_SHORT).show();
        }


        // برمجة زر التشغيل والإيقاف
        button_Play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (media_voice.isPlaying()) {
                    if (media_voice != null) {
                        media_voice.pause();
                        stopRhythm();
                        txt_Status.setText(getString(R.string.text_2));
                        button_Play.setImageResource(R.drawable.image_play);
                    }
                } else {
                    if (media_voice != null) {
                        media_voice.start();
                        startRhythm();
                        txt_Status.setText(getString(R.string.text_3));
                        button_Play.setImageResource(R.drawable.image_pause);
                    }
                }
            }
        });

        button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        });
        button_Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrevious();
            }
        });

    }

    public void processing_actionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mTitleTextView.setText(FeedMusic.feedList.get(SELECTED_POSITION).getTitle());
    }

    public void linking_elements() {
        seek_bar = (SeekBar) findViewById(R.id.seekbar);
        txt_Status = (TextView) findViewById(R.id.songCurrentDurationLabel);
        current_time = (TextView) findViewById(R.id.songCurrentDurationLabel1);
        sound_duration = (TextView) findViewById(R.id.songTotalDurationLabel);
        button_Play = (ImageButton) findViewById(R.id.btnPlay);
        image_Rhythm = (ImageView) findViewById(R.id.img_equilizer);
        image_Rhythm.setBackgroundResource(R.drawable.simple_animation);
        mAnimation = (AnimationDrawable) image_Rhythm.getBackground();
        button_Next = (ImageButton)findViewById(R.id.btnNext);
        button_Previous = (ImageButton)findViewById(R.id.btnPrevious);
    }


    public void playPrevious(){
        mHandler.removeCallbacks(mUpdateTimeTask);
        seek_bar.setProgress(0);
        if(currentSongIndex>0){
            currentSongIndex--;
            playSong(currentSongIndex);
        }
    }
    public void playNext(){
        if(currentSongIndex< FeedMusic.feedList.size()-1){
            mHandler.removeCallbacks(mUpdateTimeTask);
            seek_bar.setProgress(0);
            currentSongIndex++;
            playSong(currentSongIndex);
        }
    }

    public void playSong(int songIndex) {
        try {
            media_voice.reset();
            media_voice.setAudioStreamType(AudioManager.STREAM_MUSIC);
            media_voice.setDataSource(FeedMusic.feedList.get(songIndex).getUrl());
            txt_Status.setText(getString(R.string.text_4));

            media_voice.prepareAsync();
            media_voice.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    startRhythm();
                    updateProgressBar();
                    button_Play.setImageResource(R.drawable.image_pause);
                    txt_Status.setText(getString(R.string.text_3));
                }
            });

            media_voice.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    txt_Status.setText(getString(R.string.text_5));
                    current_time.setText("");
                    stopRhythm();
                    playNext();
                }
            });

            button_Play.setImageResource(R.drawable.image_pause);
            mTitleTextView.setText(FeedMusic.feedList.get(songIndex).getTitle());
            seek_bar.setProgress(0);
            seek_bar.setMax(100);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void startRhythm() {

        image_Rhythm.post(new Runnable() {
            public void run() {
                mAnimation.start();
            }
        });
    }

    private void stopRhythm() {
        image_Rhythm.post(new Runnable() {
            public void run() {
                mAnimation.stop();
            }
        });
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = media_voice.getDuration();
            long currentDuration = media_voice.getCurrentPosition();

            sound_duration.setText("" + utils.milliSecondsToTimer(totalDuration));
            current_time.setText("" + utils.milliSecondsToTimer(currentDuration));

            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            seek_bar.setProgress(progress);

            mHandler.postDelayed(this, 100);
        }
    };


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = media_voice.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        media_voice.seekTo(currentPosition);

        updateProgressBar();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        media_voice.release();
    }


    @Override
    public void onBackPressed() {
        mHandler.removeCallbacks(mUpdateTimeTask);
        seek_bar.setProgress(0);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {


            if(currentSongIndex < (FeedMusic.feedList.size() - 1)){
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            }else{

                playSong(0);
                currentSongIndex = 0;
            }

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(getBaseContext(), "للأسف يوجد مشكلة ما, حاول في مرة اخرى",Toast.LENGTH_LONG).show();
        finish();
        return false;
    }
}
