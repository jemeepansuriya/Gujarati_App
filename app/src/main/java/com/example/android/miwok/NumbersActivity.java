package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    MediaPlayer nm;
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
        setContentView(R.layout.activity_numbers);

       final  ArrayList<word> Numbers = new ArrayList<word>();

        Numbers.add(new word("One","એક ",R.drawable.number_one,R.raw.one));
        Numbers.add(new word("Two","બે ",R.drawable.number_two,R.raw.two));
        Numbers.add(new word("Three","ત્રણ ",R.drawable.number_three,R.raw.three));
        Numbers.add(new word("Four","ચાર ",R.drawable.number_four,R.raw.four));
        Numbers.add(new word("Five","પાંચ ",R.drawable.number_five,R.raw.five));
        Numbers.add(new word("Six","છ ",R.drawable.number_six,R.raw.six));
        Numbers.add(new word("Seven","સાત ",R.drawable.number_seven,R.raw.seven));
        Numbers.add(new word("Eight","આઠ ",R.drawable.number_eight,R.raw.eight));
        Numbers.add(new word("Nine","નવ ",R.drawable.number_nine,R.raw.nine));
        Numbers.add(new word("Ten","દશ ",R.drawable.number_ten,R.raw.ten));

        wordAdapter itemsAdapter = new wordAdapter(this,Numbers,R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word word= Numbers.get(i);
                releaseMediaPlayer();
                nm= MediaPlayer.create(NumbersActivity.this,word.getaudio());
                nm.start();
                nm.setOnCompletionListener(mCompletionListener);
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
        }
    }
}
