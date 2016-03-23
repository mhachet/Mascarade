package com.mascarade.model.cards;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Reine extends Card {

    private final int[] nbPlayersReine = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public Reine() {
        this.initialiseNbPlayers(nbPlayersReine);
    }

    public void activePower(Player concernedPlayer){
        concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 2);
    }

    public int[] getNbPlayersReine() {
        return nbPlayersReine;
    }
}
