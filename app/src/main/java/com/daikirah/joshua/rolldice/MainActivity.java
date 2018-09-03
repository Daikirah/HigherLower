package com.daikirah.joshua.rolldice;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    static int currentScoreInt;
    static int highScoreInt;
    static int currentNumber;
    static int previousNumber = 0;
    static List<String> stringList = new ArrayList<String>();
    static int[] imageResoureces =
            {
                    R.drawable.d1,
                    R.drawable.d2,
                    R.drawable.d3,
                    R.drawable.d4,
                    R.drawable.d5,
                    R.drawable.d6,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fabHigher = (FloatingActionButton) findViewById(R.id.higher_fab);
        FloatingActionButton fabLower = (FloatingActionButton) findViewById(R.id.lower_fab);

        final TextView currentScore = (TextView) findViewById(R.id.current_score);
        final TextView highScore = (TextView) findViewById(R.id.high_score);

        final ImageView imageView = (ImageView) findViewById(R.id.dice_image);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, stringList);
        listView.setAdapter(adapter);

        fabHigher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rollDice(imageView) >= previousNumber) {
                    onCorrect(currentScore);
                } else {
                    onIncorrect(highScore, currentScore);
                }
                updateUI(adapter);
                previousNumber = currentNumber;
            }
        });

        fabLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rollDice(imageView) <= previousNumber){
                    onCorrect(currentScore);
                } else {
                    onIncorrect(highScore, currentScore);
                }
                updateUI(adapter);
                previousNumber = currentNumber;
            }
        });

    }

    int rollDice(ImageView imageView) {
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 7);
        currentNumber = randomNumber;
        stringList.add("You threw " + Integer.toString(randomNumber));
        imageView.setImageResource(imageResoureces[randomNumber - 1]);
        return randomNumber;

    }


    void updateUI(ArrayAdapter adapter) {
        adapter.notifyDataSetChanged();
    }


    void onCorrect(TextView currentScoreText) {
        currentScoreInt++;
        currentScoreText.setText(Integer.toString(currentScoreInt));
        Snackbar snackbar = Snackbar.make(findViewById(R.id.rel_layout), "Correct", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    void onIncorrect(TextView highScoreText, TextView currentScoreText) {
        if (currentScoreInt > highScoreInt) {
            highScoreInt = currentScoreInt;
            highScoreText.setText(Integer.toString(highScoreInt));
        }
        currentScoreInt = 0;
        currentScoreText.setText(Integer.toString(currentScoreInt));
        Snackbar snackbar = Snackbar.make(findViewById(R.id.rel_layout), "Incorrect", Snackbar.LENGTH_SHORT);
        snackbar.show();


    }


}
