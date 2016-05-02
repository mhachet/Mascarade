package com.mascarade.model.cards;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Veuve extends Card {

    //private final int[] nbPlayersVeuve = {12, 13};

    private Player concernedPlayer;

    public Veuve() {
        super();
    }

    /**
     * The 'Veuve' power allow to win gold coins until 10.
     *
     */
    public void activePower(Player concernedPlayer){
        this.setConcernedPlayer(concernedPlayer);

        concernedPlayer.setNbMoney(10);
    }

    public Player getConcernedPlayer() {
        return concernedPlayer;
    }

    public void setConcernedPlayer(Player concernedPlayer) {
        this.concernedPlayer = concernedPlayer;
    }

    /*
    public int[] getNbPlayersVeuve() {
        return nbPlayersVeuve;
    }
    */
}
