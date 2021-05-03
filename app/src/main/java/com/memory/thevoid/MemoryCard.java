package com.memory.thevoid;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class MemoryCard {
    private Integer mCardValue;
    private boolean mRevealed = false;
    private boolean mMatched = false;

    public MemoryCard() {
        mCardValue = null;
    }

    public MemoryCard(Integer value) {
        mCardValue = value;
    }

    public boolean isMatched(){
        return mMatched;
    }

    public void setMatched(boolean match){
        mMatched = match;
    }

    public boolean isRevealed() {
        return mRevealed;
    }

    public void setRevealed(boolean revealed) {
        mRevealed = revealed;
    }

    public Integer getCardValue() {
        return mCardValue;
    }

    public void setCardValue(Integer cardValue) {
        mCardValue = cardValue;
    }
}
