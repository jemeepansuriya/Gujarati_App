package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_family);

        maudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> Numbers = new ArrayList<word>();

        Numbers.add(new word("Brother","ભાઈ  ",R.drawable.family_son,R.raw.bro));
        Numbers.add(new word("Father","પિતા ",R.drawable.family_father,R.raw.father));
        Numbers.add(new word("Mother","માતા ",R.drawable.family_mother,R.raw.mother));
        Numbers.add(new word("Sister","બહેન ",R.drawable.family_daughter,R.raw.sis));
        Numbers.add(new word("GrandFather","દાદા ",R.drawable.family_grandfather,R.raw.gfather));
        Numbers.add(new word("GrandMother","દાદી ",R.drawable.family_grandmother,R.raw.gmother));
        Numbers.add(new word("Uncle","કાકા ",R.drawable.family_older_brother,R.raw.uncle));
        Numbers.add(new word("Aunt","કાકી ",R.drawable.family_older_sister,R.raw.aunt));
        Numbers.add(new word("Grandson","પૌત્ર ",R.drawable.family_younger_brother,R.raw.grandson));
        Numbers.add(new word("Niece","ભત્રીજી ",R.drawable.family_younger_sister,R.raw.niece));

        wordAdapter itemsAdapter = new wordAdapter(this,Numbers,R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list3);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word word= Numbers.get(i);
                releaseMediaPlayer();
                int result = maudioManager.requestAudioFocus(monaudiofocuschange,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    nm = MediaPlayer.create(FamilyActivity.this, word.getaudio());
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
