package com.model.cards;

import com.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Roi extends Card {

    private final int[] nbPlayersRoi = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public Roi() {
        this.initialiseNbPlayers(nbPlayersRoi);
    }

    public void activePower(Player concernedPlayer){
        concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 3);
    }

    public int[] getNbPlayersRoi() {
        return nbPlayersRoi;
    }

}
