package com.mascarade.model.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
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

    private final static String ROUND = "ROUND";

    public Round(int nbRound, PlateauMascarade boardMascarade, Bank bank){
        this.nbRound = nbRound;
        this.boardMascarade = boardMascarade;
        this.bank = bank;
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
        Log.d(ROUND, "idStringImageViewCard : " +idStringImageView);
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        Log.d(ROUND, "cardViewTag : " + cardView.getTag());
        cardView.setImageResource(R.drawable.card_darkside);

    }


    public void showCard(Card cardPlayer, Bank bank, Activity boardMascarade){
        String idStringImageView = "imageViewCard_" + cardPlayer.getTypeCard();
        Log.d(ROUND, "idStringImageViewCard : " + idStringImageView);
        int idCardImage = cardPlayer.getIdCardImageFromCard();
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        Log.d(ROUND, "cardViewTag : " + cardView.getTag());
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
            Log.d(ROUND, "roles " + charSequenceCards.toString());
            roleCardChoicePlayerBuilder.setItems(charSequenceCards, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // of the selected item
                    String cardChosen = charSequenceCards[which].toString();
                    if (cardChosen.equals(cardMainPlayer)) {
                        //Log.d(ROUND, "good choice " + cardChosen + "  -  " + cardMainPlayer);
                        showCard(mainPlayer.getCard(), bank, boardMascarade);
                    } else {
                        Log.d(ROUND, "bad choice " + cardChosen + "  -  " + cardMainPlayer);

                    }

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
}
