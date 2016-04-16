package com.mascarade.model.game;

import android.app.Activity;
import android.widget.TextView;

import com.mascarade.R;
import com.mascarade.model.cards.Card;

import java.util.ArrayList;

/**
 * Created by melanie on 15/03/16.
 */
public class Tribunal {

    private int nbMoney;
    private Activity boardMascarade;

    public Tribunal(int nbMoney, Activity boardMascarade) {
        this.nbMoney = nbMoney;
        this.boardMascarade = boardMascarade;
    }

    public int getNbMoney() {
        return nbMoney;
    }

    public void setNbMoney(int nbMoney) {
        this.nbMoney = nbMoney;
        TextView textView_tribunal_gold = (TextView)boardMascarade.findViewById(R.id.textView_tribunal_gold);
        textView_tribunal_gold.setText(Integer.toString(nbMoney));
    }

    public Activity getBoardMascarade() {

        return boardMascarade;
    }

    public void setBoardMascarade(Activity boardMascarade) {

        this.boardMascarade = boardMascarade;
    }
}
