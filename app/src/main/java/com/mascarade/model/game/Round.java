package com.mascarade.model.game;

import com.mascarade.model.activities.PlateauMascarade;
import com.mascarade.model.cards.Card;

import java.util.ArrayList;

/**
 * Created by melanie on 4/3/16.
 */
public class Round {

    private int nbRound = 0;
    private PlateauMascarade mascarade;

    public Round(int nbRound, PlateauMascarade mascarade){
        this.nbRound = nbRound;
        this.mascarade = mascarade;
    }

    public void drawCardsRoundZero(Bank newGane){

        ArrayList<Player> playerArrayList = newGane.getListPlayers();
        for(int i = 0 ; i < playerArrayList.size() ; i++){
            Player player = playerArrayList.get(i);
            Card playerCard = player.getCard();
            playerCard.setVisibleAll(true);
        }

    }
}
