package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    MediaPlayer nm;
    AudioManager maudioManager;

    private AudioManager.OnAudioFocusChangeListener monaudiofocuschange=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                nm.pause();
                nm.seekTo(0);
            } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                nm.start();
            } else if (i == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        maudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> Colors = new ArrayList<word>();

        Colors.add(new word("Red", "લાલ",R.drawable.color_red,R.raw.red));
        Colors.add(new word("Blue", "બ્લુ",R.drawable.color_dusty_yellow,R.raw.blue));
        Colors.add(new word("Yellow", "પીળો",R.drawable.color_mustard_yellow,R.raw.yellow));
        Colors.add(new word("Green", "લીલો",R.drawable.color_green,R.raw.green));
        Colors.add(new word("Cyan", "વાદળી",R.drawable.color_dusty_yellow,R.raw.cyan));
        Colors.add(new word("Black", "કાળો",R.drawable.color_black,R.raw.black));
        Colors.add(new word("White", "સફેદ",R.drawable.color_white,R.raw.white));
        Colors.add(new word("brown", "ભૂરો",R.drawable.color_brown,R.raw.brown));
        Colors.add(new word("Orange", "નારંગી",R.drawable.color_red,R.raw.orange));
        Colors.add(new word("Gray", "\t\n" +
                "રાખોડી",R.drawable.color_gray,R.raw.gray));

        wordAdapter itemsAdapter = new wordAdapter(this, Colors,R.color.category_colors);
        ListView listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word word= Colors.get(i);
                releaseMediaPlayer();
                int result = maudioManager.requestAudioFocus(monaudiofocuschange,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    nm = MediaPlayer.create(ColorsActivity.this, word.getaudio());
                    nm.start();
                    nm.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    private void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if (nm != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            nm.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            nm = null;
            maudioManager.abandonAudioFocus(monaudiofocuschange);
        }
    }
}