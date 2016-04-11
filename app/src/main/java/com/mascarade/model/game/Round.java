package com.mascarade.model.game;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mascarade.R;
import com.mascarade.model.activities.PlateauMascarade;
import com.mascarade.model.cards.Card;

import java.util.ArrayList;

/**
 * Created by melanie on 4/3/16.
 */
public class Round {

    private int nbRound = 0;
    private PlateauMascarade mascarade;
    private final static String ROUND = "ROUND";

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

    public void hideCard(int idCard, Bank bank, Activity boardMascarade){
        Log.d(ROUND, "id card : " + idCard);
        ImageView cardView = (ImageView) boardMascarade.findViewById(idCard);

        //ImageView darkSide = new ImageView(R.drawable.card_darkside);

        Log.d(ROUND, "id image : " + R.drawable.card_darkside);
        cardView.setImageResource(R.drawable.card_darkside);

    }
}
