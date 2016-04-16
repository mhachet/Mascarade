package com.mascarade.model.cards;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Sorciere extends Card {

    //private final int[] nbPlayersSorciere = {5, 6, 7, 8, 9, 10, 11, 12, 13};

    public Sorciere(){
    }

    /**
     * The "Sorciere" power is to change all her own gold with another player.
     *
     * @param concernedPlayer
     * @param opponentPlayer
     */
    public void activePower(Player concernedPlayer, Player opponentPlayer){
        int nbMoneyConcerned = concernedPlayer.getNbMoney();
        int nbMoneyOpponent = opponentPlayer.getNbMoney();

        concernedPlayer.setNbMoney(nbMoneyOpponent);
        opponentPlayer.setNbMoney(nbMoneyConcerned);
    }

    /*public int[] getNbPlayersSorciere() {
        return nbPlayersSorciere;
    }
    */
}
