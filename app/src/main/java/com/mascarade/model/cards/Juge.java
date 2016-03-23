package com.mascarade.model.cards;

import com.mascarade.model.game.Player;
import com.mascarade.model.game.Tribunal;

/**
 * Created by melanie on 12/03/16.
 */
public class Juge extends Card {

    private final int[] nbPlayersJuge = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public Juge() {
        this.initialiseNbPlayers(nbPlayersJuge);
    }


    /**
     * The Juge power is to get all tribunal money
     *
     * @param concernedPlayer
     * @param tribunal
     */
    public void activePower(Player concernedPlayer, Tribunal tribunal) {
        int tribunalMoney = tribunal.getNbMoney();
        concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + tribunalMoney);
        tribunal.setNbMoney(0);
    }

    public int[] getNbPlayersJuge() {
        return nbPlayersJuge;
    }
}
