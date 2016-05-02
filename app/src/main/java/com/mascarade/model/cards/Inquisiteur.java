package com.mascarade.model.cards;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Inquisiteur extends Card {

    //private final int[] nbPlayersInquisiteur = {11, 12, 13};
    private Player opponenentPlayer;

    public Inquisiteur(){
        super();
    }

    public void activePower() {

    }

    public Player getOpponenentPlayer() {
        return opponenentPlayer;
    }

    public void setOpponenentPlayer(Player opponenentPlayer) {
        this.opponenentPlayer = opponenentPlayer;
    }

    //public int[] getNbPlayersInquisiteur() {return nbPlayersInquisiteur;}

}
