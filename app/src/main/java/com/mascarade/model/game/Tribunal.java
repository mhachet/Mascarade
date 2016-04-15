package com.mascarade.model.game;

import com.mascarade.model.cards.Card;

import java.util.ArrayList;

/**
 * Created by melanie on 15/03/16.
 */
public class Tribunal {

    private int nbMoney;

    public Tribunal(int nbMoney) {
        this.nbMoney = nbMoney;
    }

    public int getNbMoney() {
        return nbMoney;
    }

    public void setNbMoney(int nbMoney) {
        this.nbMoney = nbMoney;
    }

}
