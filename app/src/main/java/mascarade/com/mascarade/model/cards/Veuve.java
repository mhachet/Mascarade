package com.model.cards;

import com.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Veuve extends Card {

    private final int[] nbPlayersVeuve = {12, 13};

    public Veuve() {
        this.initialiseNbPlayers(nbPlayersVeuve);
    }

    /**
     * The 'Veuve' power allow to win gold coins until 10.
     *
     * @param concernedPlayer
     */
    public void activePower(Player concernedPlayer){
        concernedPlayer.setNbMoney(10);
    }

    public int[] getNbPlayersVeuve() {
        return nbPlayersVeuve;
    }
}
