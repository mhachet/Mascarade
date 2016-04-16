package com.mascarade.model.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.Image;
import android.media.tv.TvView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private PlateauMascarade boardMascarade;
    private Bank bank;
    private Tribunal tribunal;

    private final static String ROUND = "ROUND";

    public Round(int nbRound, PlateauMascarade boardMascarade, Bank bank, Tribunal tribunal){
        this.nbRound = nbRound;
        this.boardMascarade = boardMascarade;
        this.bank = bank;
        this.tribunal = tribunal;
    }

    public void activeChangeCardButton(){
        Button changeCard = (Button)boardMascarade.findViewById(R.id.button_changeCard);
        changeCard.setOnClickListener(new ButtonChangeCardOnClickListener(changeCard));
    }

    public void activeSeeCardButton(){
        Button seeCardButton = (Button)boardMascarade.findViewById(R.id.button_seeCard);
        seeCardButton.setOnClickListener(new ButtonSeeCardOnClickListener(seeCardButton));
    }

    public void activeAnnounceCardButton(){
        Button announceCardButton = (Button)boardMascarade.findViewById(R.id.button_announceCard);
        announceCardButton.setOnClickListener(new ButtonAnnounceCardOnClickListener(announceCardButton, boardMascarade));

    }

    public void hideCard(Card playerCard, Bank bank, Activity boardMascarade){

        String idStringImageView = "imageViewCard_" + playerCard.getTypeCard();
        //Log.d(ROUND, "idStringImageViewCard : " +idStringImageView);
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        //Log.d(ROUND, "cardViewTag : " + cardView.getTag());
        cardView.setImageResource(R.drawable.card_darkside);

    }


    public void showCard(Card cardPlayer, Bank bank, Activity boardMascarade){
        String idStringImageView = "imageViewCard_" + cardPlayer.getTypeCard();
        //Log.d(ROUND, "idStringImageViewCard : " + idStringImageView);
        int idCardImage = cardPlayer.getIdCardImageFromCard();
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        //Log.d(ROUND, "cardViewTag : " + cardView.getTag());
        cardView.setImageResource(idCardImage);
    }

    class ButtonChangeCardOnClickListener implements View.OnClickListener{

        private final Button buttonChangeCard;

        ButtonChangeCardOnClickListener(Button button){
            this.buttonChangeCard = button;
        }
        @Override
        public void onClick(View v){

        }
    }

    class ButtonAnnounceCardOnClickListener implements View.OnClickListener{

        private final Button buttonAnnounceCard;
        private final PlateauMascarade boardMascarade;

        ButtonAnnounceCardOnClickListener(Button buttonAnnounceCard, PlateauMascarade boardMascarade){
            this.buttonAnnounceCard = buttonAnnounceCard;
            this.boardMascarade = boardMascarade;
        }
        @Override
        public void onClick(View v){
            ArrayList<Card> listCards = bank.getBankCardsListStart();
            ArrayList<String> listCardsType = new ArrayList<>();
            for(int i = 0; i < listCards.size(); i++){
                String cardType = listCards.get(i).getTypeCard();
                listCardsType.add(cardType);
            }
            final CharSequence[] charSequenceCards = listCardsType.toArray(new CharSequence[listCardsType.size()]);
            final Player mainPlayer = bank.getMainPlayer();
            final String cardMainPlayer = mainPlayer.getTypeCard();
            AlertDialog.Builder roleCardChoicePlayerBuilder = new AlertDialog.Builder(boardMascarade);
            roleCardChoicePlayerBuilder.setTitle("Quel carte êtes vous ?");
            roleCardChoicePlayerBuilder.setItems(charSequenceCards, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // of the selected item
                    String cardChosen = charSequenceCards[which].toString();
                    if (cardChosen.equals(cardMainPlayer)) {
                        //Log.d(ROUND, "good choice " + cardChosen + "  -  " + cardMainPlayer);
                        showCard(mainPlayer.getCard(), bank, boardMascarade);
                        activePowerSorciere(mainPlayer);
                    } else {
                        Log.d(ROUND, "bad choice " + cardChosen + "  -  " + cardMainPlayer);
                        AlertDialog.Builder builder = new AlertDialog.Builder(boardMascarade);
                        builder.setTitle("Vous vous êtes trompé de rôle");
                        builder.setMessage("Vous donnez 1 pièce d'or au Tribunal")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // give 1 gold to the "Tribunal"
                                    }
                                });
                        builder.create();
                        builder.show();

                    }
                    mainPlayer.annoucementCard(cardChosen, tribunal);

                }

            });

            AlertDialog roleCardChoicePlayerAlert = roleCardChoicePlayerBuilder.create();
            roleCardChoicePlayerAlert.show();
        }
    }

    class ButtonSeeCardOnClickListener implements View.OnClickListener{

        private final Button buttonSeeCard;

        ButtonSeeCardOnClickListener(Button button){
            this.buttonSeeCard = button;
        }
        @Override
        public void onClick(View v){

        }
    }

    public void activePowerSorciere(Player playerConcerned){
        ArrayList<Player> playerArrayList = bank.getListPlayers();
        ArrayList<String> otherPlayers = new ArrayList<>();
        for(int i = 0; i < playerArrayList.size(); i++){
            Player player = playerArrayList.get(i);
            String playerName = player.getName();
            if(!player.equals(playerConcerned)){
                otherPlayers.add(playerName);
            }
        }
        final CharSequence[] charSequenceOthersPlayers = otherPlayers.toArray(new CharSequence[otherPlayers.size()]);
        AlertDialog.Builder sorciereChoiceOpponentBuilder = new AlertDialog.Builder(boardMascarade);
        sorciereChoiceOpponentBuilder.setTitle("Vous activez le pouvoir de la Sorcière");
        //sorciereChoiceOpponentBuilder.setMessage("Choisissez avec qui vous souhaitez échangez votre or");
        sorciereChoiceOpponentBuilder.setItems(charSequenceOthersPlayers, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String playerChosen = charSequenceOthersPlayers[which].toString();
                Log.d(ROUND, "other player chosen : " + playerChosen);
            }
        });

        sorciereChoiceOpponentBuilder.create();
        sorciereChoiceOpponentBuilder.show();
    }

}
