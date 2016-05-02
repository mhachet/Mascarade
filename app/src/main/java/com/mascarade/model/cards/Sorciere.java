package com.mascarade.model.cards;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Sorciere extends Card {

    //private final int[] nbPlayersSorciere = {5, 6, 7, 8, 9, 10, 11, 12, 13};

    private Player concernedPlayer;
    private Player opponentPlayer;

    public Sorciere(){
    }

    /**
     * The "Sorciere" power is to change all her own gold with another player.
     *
     */
    public void activePower(Player concernedPlayer, Player opponentPlayer){
        this.setConcernedPlayer(concernedPlayer);
        this.setOpponentPlayer(opponentPlayer);

        int nbMoneyConcerned = concernedPlayer.getNbMoney();
        int nbMoneyOpponent = opponentPlayer.getNbMoney();

        concernedPlayer.setNbMoney(nbMoneyOpponent);
        opponentPlayer.setNbMoney(nbMoneyConcerned);
    }

    public Player getConcernedPlayer() {
        return concernedPlayer;
    }

    public void setConcernedPlayer(Player concernedPlayer) {
        this.concernedPlayer = concernedPlayer;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    /*public int[] getNbPlayersSorciere() {
        return nbPlayersSorciere;
    }
    */
}
