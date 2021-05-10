package com.memory.thevoid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity8 extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private static Context context;
    int indexOfRevealedCard = -1;   //Stores location of currently revealed card, -1 if there is no currently revealed card
    static int score = 0;  //Stores score
    int numOfRevealedCards = 0;  //Stores the number of currently revealed cards, used for TryAgain button

    // Size could be initialized with a different value for larger or smaller decks
    final int SIZE = 8;

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

    //Create an array of the images, with two of each because we need pairs
    Integer[] images = {R.drawable.ic_android, R.drawable.ic_monkey, R.drawable.ic_blender, R.drawable.ic_toast,
            R.drawable.ic_android, R.drawable.ic_monkey, R.drawable.ic_blender, R.drawable.ic_toast};

    ImageButton[] buttons; //Create an array of ImageButtons

    //Other activity elements
    TextView mScore;
    Button mTryAgain;
    Button mEndGame;
    Button mNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game8);
        GameActivity8.context = getApplicationContext();
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

        // Initialize array of image buttons
        buttons = new ImageButton[SIZE];
        buttons[0] = mButton1;
        buttons[1] = mButton2;
        buttons[2] = mButton3;
        buttons[3] = mButton4;
        buttons[4] = mButton5;
        buttons[5] = mButton6;
        buttons[6] = mButton7;
        buttons[7] = mButton8;

        //Attach other elements to XML counterparts
        mTryAgain = (Button) findViewById(R.id.try_again_button);
        mNewGame = (Button) findViewById(R.id.new_game_button);
        mEndGame = (Button) findViewById(R.id.end_game_button);
        mScore = (TextView) findViewById(R.id.score_view);

        List<Integer> imageList = Arrays.asList(images);    //"Cast" that array into a list so we can use the shuffle function
        Collections.shuffle(imageList); //Randomizes the images, placing them in random places
        imageList.toArray(images);  //Places the shuffled images back into images[] array

        if(savedInstanceState != null) {    //Restores the values after rotation
            images = (Integer[]) savedInstanceState.getSerializable("images");  //Restores all relevent values
            score = savedInstanceState.getInt("score");
            mScore.setText(savedInstanceState.getString("scoreView"));
            numOfRevealedCards = savedInstanceState.getInt("numOfRevealedCards");

            boolean[] revealed = (boolean[]) savedInstanceState.getSerializable("revealed");    //Gets the revealed status of each card
            boolean[] matched = (boolean[]) savedInstanceState.getSerializable("matched");  //Gets the matched status of each card
            int tempIndex = 0;
            for(MemoryCard card : deck) {
                card.setRevealed(revealed[tempIndex]);  //Sets the revealed status of each card
                card.setMatched(matched[tempIndex]);    //Sets the  matched status of each card
                tempIndex++;
            }
        }

        for(int i = 0; i < buttons.length; i++) {
            deck[i].setCardValue(images[i]); //Sets the value for each MemoryCard
        }

        updateViews(buttons);   //Updates views just in case there was a saved instance state

        int index = 0;  //Using "modern" for loop, must keep track of index separately

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
            index++;  //Increment index
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
        mEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
            score += 2;  //...Increment score by two...
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
        if (gameEnded(deck)) {
            openDialog(this.getWindow().getDecorView());
            score = 0;
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = null;
        switch(item.getItemId()) {
            case R.id.tile4:
                intent = new Intent(this, GameActivity4.class);
                startActivity(intent);
                return true;
            case R.id.tile6:
                intent = new Intent(this, GameActivity6.class);
                startActivity(intent);
                return true;
            case R.id.tile8:
                intent = new Intent(this, GameActivity8.class);
                startActivity(intent);
                return true;
            case R.id.tile10:
                intent = new Intent(this, GameActivity10.class);
                startActivity(intent);
                return true;
            case R.id.tile12:
                intent = new Intent(this, GameActivity12.class);
                startActivity(intent);
                return true;
            case R.id.tile14:
                intent = new Intent(this, GameActivity14.class);
                startActivity(intent);
                return true;
            case R.id.tile16:
                intent = new Intent(this, GameActivity16.class);
                startActivity(intent);
                return true;
            case R.id.tile18:
                intent = new Intent(this, GameActivity18.class);
                startActivity(intent);
                return true;
            case R.id.tile20:
                intent = new Intent(this, GameActivity20.class);
                startActivity(intent);
                return true;
            default:
                intent = new Intent(this, GameActivity8.class);
                startActivity(intent);
                return true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {  //Saves values during rotation
        super.onSaveInstanceState(outState);

        int index = 0;
        boolean[] revealed = {false, false, false, false, false, false, false, false};
        boolean[] matched = {false, false, false, false, false, false, false, false};
        for(MemoryCard card : deck) {   //Stores the revealed and matched status of each card in an array
            revealed[index] = card.isRevealed();
            matched[index] = card.isMatched();
            index++;
        }

        outState.putSerializable("images", images); //Saves all relevant values to be restored after rotation
        outState.putInt("score", score);
        outState.putInt("numOfRevealedCards", numOfRevealedCards);
        outState.putString("scoreView", mScore.getText().toString());
        outState.putSerializable("revealed", revealed);
        outState.putSerializable("matched", matched);
    }

    public void openDialog(View view) {
        Custom_Dialog custom_dialog = new Custom_Dialog();
        custom_dialog.show(getSupportFragmentManager(), null);
    }

    public static int getScore () {
        return score;
    }

    private boolean gameEnded(MemoryCard[] d) {
        int num = SIZE;
        int matches = 0;
        for (MemoryCard card: d) {
            if (card.isMatched())
                matches++;
        }

        return num == matches;
    }
}