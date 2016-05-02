package com.mascarade.model.cards;

import android.util.Log;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Reine extends Card {

    //private final int[] nbPlayersReine = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private final static String REINE = "REINE";

    private Player concernedPlayer;


    public Reine() {
        super();
    }

    public Reine (Player player){

        this.concernedPlayer = player;
    }

    public void activePower(Player concernedPlayer){
        this.setConcernedPlayer(concernedPlayer);
        concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 2);
    }

    public Player getConcernedPlayer() {

        return concernedPlayer;
    }

    public void setConcernedPlayer(Player concernedPlayer) {
        this.concernedPlayer = concernedPlayer;
    }

    /*
    public int[] getNbPlayersReine() {
        return nbPlayersReine;
    }
    */
}
