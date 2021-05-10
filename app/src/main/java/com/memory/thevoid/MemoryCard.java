package com.memory.thevoid;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class MemoryCard {
    private static int matches;
    private Integer mCardValue;
    private boolean mRevealed = false;
    private boolean mMatched = false;

    public MemoryCard() {
        matches = 0;
        mCardValue = null;
    }

    public MemoryCard(Integer value) {
        matches = 0;
        mCardValue = value;
    }

    public boolean isMatched() { return mMatched;
    }

    public void setMatched(boolean match) {
        matches++;
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

    public int getMatches() {
        return matches;
    }
}