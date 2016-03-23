package com.model.game;

import com.model.cards.Card;

import java.util.ArrayList;

/**
 * Created by melanie on 15/03/16.
 */
public class Tribunal {

    private int nbMoney = 0;
    private ArrayList<Card> cardArrayList;

    public Tribunal(ArrayList<Card> cardsInGame) {
        this.cardArrayList = cardsInGame;
    }

    public int getNbMoney() {
        return nbMoney;
    }

    public void setNbMoney(int nbMoney) {
        this.nbMoney = nbMoney;
    }

    public ArrayList<Card> getCardArrayList() {
        return cardArrayList;
    }

    public void setCardArrayList(ArrayList<Card> cardArrayList) {
        this.cardArrayList = cardArrayList;
    }
}
