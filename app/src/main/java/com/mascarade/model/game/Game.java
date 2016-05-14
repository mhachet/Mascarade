package com.mascarade.model.game;

import android.widget.TextView;

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

    public void startGame (PlateauMascarade boardMascarade, Tribunal tribunal){
        Player winner = bank.getWinner();
        if(winner == null){
            countRound ++;
            Round round = new Round(countRound, boardMascarade, bank, tribunal);
            round.setNbRound(countRound);

            ArrayList<Round> listRoundsGame = this.getListRounds();
            listRoundsGame.add(round);
            this.addListenersActionPlayer(boardMascarade);
        }
    }

    public void addListenersActionPlayer(PlateauMascarade boardMascarade){
        Round currentRound = this.getCurrentRound();
        if(countRound > 4){

            currentRound.activeAnnounceCardButton();
            currentRound.activeChangeCardButton();
            currentRound.activeSeeCardButton();
            String actionChoosen = currentRound.getActionChoosen();
            Player opponentPlayer = currentRound.getOpponentPlayer();

        }
        else{
            currentRound.activeChangeCardButton();

            String actionChoosen = currentRound.getActionChoosen();
            Player opponentPlayer = currentRound.getOpponentPlayer();
            //this.finishRound(boardMascarade, actionChoosen, opponentPlayer);
        }


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
