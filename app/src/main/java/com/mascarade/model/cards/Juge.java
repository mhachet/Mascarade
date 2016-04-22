package com.mascarade.model.cards;

import com.mascarade.model.game.Player;
import com.mascarade.model.game.Tribunal;

/**
 * Created by melanie on 12/03/16.
 */
public class Juge extends Card {

    //private final int[] nbPlayersJuge = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    private Player concernedPlayer;
    private Tribunal tribunal;


    public Juge(){
    }


    /**
     * The Juge power is to get all tribunal money
     *
     * Player concernedPlayer, Tribunal tribunal
     */
    public void activePower() {
        int tribunalMoney = tribunal.getNbMoney();
        concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + tribunalMoney);
        tribunal.setNbMoney(0);

        //return tribunalMoney;
    }

    public Player getConcernedPlayer() {
        return concernedPlayer;
    }

    public void setConcernedPlayer(Player concernedPlayer) {
        this.concernedPlayer = concernedPlayer;
    }

    public Tribunal getTribunal() {
        return tribunal;
    }

    public void setTribunal(Tribunal tribunal) {
        this.tribunal = tribunal;
    }

    //public int[] getNbPlayersJuge() {return nbPlayersJuge;}
}
