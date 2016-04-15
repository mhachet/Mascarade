package com.mascarade.model.game;

import com.mascarade.model.activities.PlateauMascarade;

import java.util.ArrayList;

/**
 * Created by melanie on 4/13/16.
 */
public class Game {

    private ArrayList<Round> listRounds = new ArrayList<>();
    private Bank bank;

    private int countRound = 0;

    final private String GAME = "GAME";

    public Game(Bank bank){

        this.bank = bank;
    }

    public void startGame (){

    }

    public void addListenersActionPlayer(PlateauMascarade boardMascarade){

        //if(countRound > 4){
            Round currentRound = this.getCurrentRound();
            currentRound.activeAnnounceCardButton();
            currentRound.activeChangeCardButton();
            currentRound.activeSeeCardButton();
        //}
    }



    public ArrayList<Round> getListRounds() {
        return listRounds;
    }

    public void setListRounds(ArrayList<Round> listRounds) {
        this.listRounds = listRounds;
    }

    public Round getCurrentRound(){
        ArrayList<Round> roundArrayList = this.getListRounds();
        Round lastRound = roundArrayList.get(roundArrayList.size() - 1 );

        return lastRound;
    }

    public int getCountRound() {
        return countRound;
    }

    public void setCountRound(int countRound) {
        this.countRound = countRound;
    }
}
