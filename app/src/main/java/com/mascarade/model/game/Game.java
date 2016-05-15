package com.mascarade.model.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.mascarade.R;
import com.mascarade.model.activities.PlateauMascarade;
import com.mascarade.model.cards.Card;

import java.util.ArrayList;

/**
 * Created by melanie on 4/13/16.
 */
public class Game {

    private ArrayList<Round> listRounds = new ArrayList<>();
    private Bank bank;
    private PlateauMascarade boardMascarade;

    private int countRound = 0;

    final private String GAME = "GAME";

    public Game(Bank bank, PlateauMascarade boardMascarade){

        this.bank = bank;
        this.boardMascarade = boardMascarade;
    }

    public void startNewRound(Tribunal tribunal){
        Player winner = bank.getWinner();
        if(winner == null){
            countRound ++;
            Round round = new Round(countRound, boardMascarade, bank, tribunal, this);
            round.setNbRound(countRound);

            ArrayList<Round> listRoundsGame = this.getListRounds();
            listRoundsGame.add(round);
            this.addListenersActionPlayer();
        }
    }

    public void addListenersActionPlayer(){
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

    public void finishRound(final String action, final Tribunal tribunal){

        final Player playerConcerned = bank.getMainPlayer();
        final Card cardPlayerConcerned = playerConcerned.getCard();
        final String idCard = Integer.toString(playerConcerned.getId());
        final int nbPlayers = bank.getNbPlayers();
        final ArrayList<Card> listCards = bank.getBankCardsListStart();
        final AlertDialog.Builder builderNext = new AlertDialog.Builder(boardMascarade);
        builderNext.setTitle("Votre tour est terminé")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
                        textViewInstruction.setText("Votre tour est terminé : " + action);
                        if (action.equals("see")) {
                            //cardPlayerConcerned.hideCard(idCard, boardMascarade);
                            playerConcerned.setLastCardKnown(cardPlayerConcerned.getTypeCard());
                        } else if (action.equals("power")) {
                            //cardPlayerConcerned.hideCard(idCard, boardMascarade);
                            /*
                            if(opponentPlayer != null){
                                TextView textView = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
                                textView.setText("opponent : " + opponentPlayer.getTypeCard());
                                Card cardOpponent = opponentPlayer.getCard();
                                String idOpponentPlayer = Integer.toString(opponentPlayer.getId());
                                cardOpponent.hideCard(idOpponentPlayer, boardMascarade);
                            }
                            */
                            for(int i = 0 ; i < listCards.size() - 1 ; i++){
                                Card card = listCards.get(i);
                                card.hideCard(Integer.toString(card.getPlayer().getId()), boardMascarade);
                            }
                        }
                        startNewRound(tribunal);
                    }
                });
        builderNext.create();
        builderNext.show();
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
