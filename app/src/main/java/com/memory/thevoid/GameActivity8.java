package com.memory.thevoid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity8 extends AppCompatActivity {
    int indexOfRevealedCard = -1;   //Stores location of currently revealed card, -1 if there is no currently revealed card
    int score = 0;  //Stores score
    int numOfRevealedCards = 0;  //Stores the number of currently revealed cards, used for TryAgain button
    //Create the necessary buttons
    ImageButton mButton1;
    ImageButton mButton2;
    ImageButton mButton3;
    ImageButton mButton4;
    ImageButton mButton5;
    ImageButton mButton6;
    ImageButton mButton7;
    ImageButton mButton8;
    //Creates the "deck" of MemoryCard objects
    MemoryCard[] deck = {new MemoryCard(), new MemoryCard(), new MemoryCard(), new MemoryCard(), new MemoryCard(), new MemoryCard(), new MemoryCard(), new MemoryCard()};

    //Other activity elements
    TextView mScore;
    Button mTryAgain;
    Button mEndGame;
    Button mNewGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game8);
        final String TAG = "GameActivity8";

        //Attach buttons to XML counterparts
        mButton1 = (ImageButton) findViewById(R.id.card_one);
        mButton2 = (ImageButton) findViewById(R.id.card_two);
        mButton3 = (ImageButton) findViewById(R.id.card_three);
        mButton4 = (ImageButton) findViewById(R.id.card_four);
        mButton5 = (ImageButton) findViewById(R.id.card_five);
        mButton6 = (ImageButton) findViewById(R.id.card_six);
        mButton7 = (ImageButton) findViewById(R.id.card_seven);
        mButton8 = (ImageButton) findViewById(R.id.card_eight);

        //Attach other elements to XML counterparts
        mTryAgain = (Button) findViewById(R.id.try_again_button);
        mNewGame = (Button) findViewById(R.id.new_game_button);
        mEndGame = (Button) findViewById(R.id.end_game_button);
        mScore = (TextView) findViewById(R.id.score_view);

        ImageButton[] buttons = {mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7, mButton8};   //Create an array of ImageButtons

        //Create an array of the images, with two of each because we need pairs
        Integer[] images = {R.drawable.ic_android, R.drawable.ic_monkey, R.drawable.ic_blender, R.drawable.ic_toast,
                            R.drawable.ic_android, R.drawable.ic_monkey, R.drawable.ic_blender, R.drawable.ic_toast};
        List<Integer> imageList = Arrays.asList(images);    //"Cast" that array into a list so we can use the shuffle function
        Collections.shuffle(imageList); //Randomizes the images, placing them in random places
        imageList.toArray(images);  //Places the shuffled images back into images[] array

        for(int i = 0; i<buttons.length; i++) {
            deck[i] = new MemoryCard(images[i]);    //Sets the value for each MemoryCard
        }

        int index = 0;  //Using "modern" for loop, must keep track of index separatley
        //This for loop adds buttonListeners to all the buttons. You should be able to copy paste this to other activities to create more pages
        for (ImageButton button : buttons) {
            int finalIndex = index; //JVM gets angry if you use a non-final int, so we cast it to a final int
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MemoryCard card = deck[finalIndex];
                    //Update the model (MemoryCard/Logic)
                    updateModels(finalIndex, card);
                    //Update the view (The stuff you see)
                    updateViews(buttons);
                }
            });
            index++;    //Increment index
        }

        //Set listeners for other buttons
        mTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numOfRevealedCards >= 2) {   //Only let user try again if they have selected two cards
                    restoreCards(); //Reset revealed cards
                    updateViews(buttons);   //Update view to reflect that
                    numOfRevealedCards = 0; //There are now 0 revealed cards, update int to reflect that
                }

            }
        });
        mNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent()); //These two lines of code refresh the activity, effectivly restarting it

            }
        });
        mEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameActivity8.this, MainActivity.class);
                startActivity(i);   //Return to MainActivity
            }
        });
    }

    private void updateViews(ImageButton[] buttons) {
        int index = 0;
        for(MemoryCard card : deck) {
            ImageButton button = buttons[index];
            if(card.isRevealed()) {
                button.setImageResource(card.getCardValue());    //Sets the image to the corresponding image in the images list
            }
            else {
                button.setImageResource(R.drawable.ic_baseline);    //I don't know if we need this anymore, but I left it anyway
            }
            index++;
        }

    }

    private void updateModels(int index, MemoryCard card) {
        if(card.isRevealed()) { //If the card is revealed, don't let them choose it again
            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(numOfRevealedCards >= 2) {   //If two (or more) cards are currently revealed, don't let the user do anything until they press try again
            return;
        }
        if(indexOfRevealedCard == -1) { //No cards currently revealed
            indexOfRevealedCard = index;    //Save the index of the current card
            numOfRevealedCards++;   //When we reveal a card, increment the number of revealed cards
        }
        else {  //A card is currently revealed, must compare them
            checkForMatch(index, indexOfRevealedCard);  //Check the two cards to see if they match
            indexOfRevealedCard = -1;   //Reset the value of the saved card
        }

        Log.i("GameActivity8", String.valueOf(numOfRevealedCards));
        card.setRevealed(!card.isRevealed());   //After the user clicks a card, reveal it
    }

    private void restoreCards() {
        for(MemoryCard card : deck) {   //Go over the whole "deck"
            if (!card.isMatched()) {    //If the card ISN'T MATCHED...
                card.setRevealed(false);    //...set it's revealed status to false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkForMatch(int index1, int index2) {
        String tempScore;   //Stores the value of score in a string
        if(deck[index1].getCardValue().equals(deck[index2].getCardValue())) {   //If the cards match....
            Toast.makeText(this,"Match found!", Toast.LENGTH_SHORT).show(); //...notify the user....
            score = score + 2;  //...Increment score by two...
            tempScore = String.valueOf(score);
            mScore.setText(tempScore);  //...Display the score...
            deck[index1].setMatched(true);
            deck[index2].setMatched(true);  //...And set both of them to matched
            numOfRevealedCards = 0; //The cards are matched, and so are no longer considered revealed
        }
        else {  //If cards don't match...
            score--;    //...Subtract one point...
            if(score < 0) { //...But don't let the score go below 0...
                score = 0;
            }
            tempScore = String.valueOf(score);
            mScore.setText(tempScore);  //...And display the score
            numOfRevealedCards++;   //When we reveal a card, increment the number of revealed cards
        }
    }
}