package com.mascarade.model.cards;

import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;

import java.util.ArrayList;

/**
 * Created by melanie on 12/03/16.
 */
public class Voleur extends Card {

    private final int[] nbPlayersVoleur = {4, 7, 13};

    public void activePower(Player concernedPlayer, Bank bank){
        int nbPlayers = bank.getNbPlayers();
        int idConcernedPlayer = concernedPlayer.getId();
        ArrayList<Player> playerArrayList = bank.getListPlayers();

        if(idConcernedPlayer == 0){
            Player rightPlayer = playerArrayList.get(12);
            Player leftPlayer = playerArrayList.get(1);
        }
        else if(idConcernedPlayer == nbPlayers - 1){

        }

    }

    public Voleur() {
        this.initialiseNbPlayers(nbPlayersVoleur);
    }
}
