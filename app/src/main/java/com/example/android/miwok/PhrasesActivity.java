package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_phrases);

        final ArrayList<word> Colors = new ArrayList<word>();

        Colors.add(new word(
                "Where are you going?", "તમે ક્યાં જાવ છો? ",R.raw.where));
        Colors.add(new word("What is your name?", "તમારું નામ શું છે? ",R.raw.name));
        Colors.add(new word("My name is...", "મારું નામ... ",R.raw.marunam));
        Colors.add(new word(
                "How are you feeling?", "તમને કેવું લાગે છે? ",R.raw.kevu));
        Colors.add(new word("I’m feeling good.", "મને સારુ લાગી રહ્યુ છે. ",R.raw.saru));
        Colors.add(new word("Are you coming?", "શું તમે આવો છો?",R.raw.shu));
        Colors.add(new word("Yes, I’m coming.", "હા, હું આવું છું ",R.raw.ha));
        Colors.add(new word("I’m coming.", "હું આવું છુ.",R.raw.huaavuchu));
        Colors.add(new word(
                "Let’s go.", "ચાલો જઇએ.",R.raw.chalo));
        Colors.add(new word(
                "Come here", "અહી આવો ",R.raw.aavo));

        wordAdapter itemsAdapter = new wordAdapter(this, Colors,R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list2);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word word= Colors.get(i);
                releaseMediaPlayer();
                nm= MediaPlayer.create(PhrasesActivity.this,word.getaudio());
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
