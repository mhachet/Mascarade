package com.mascarade.model.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mascarade.R;
import com.mascarade.model.activities.PlateauMascarade;
import com.mascarade.model.cards.Card;
import com.mascarade.model.cards.Eveque;
import com.mascarade.model.cards.Juge;
import com.mascarade.model.cards.Paysan;
import com.mascarade.model.cards.Sorciere;

import org.w3c.dom.Text;

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

    public void hideCard(Card playerCard, String idCard, Activity boardMascarade){

        String idStringImageView = "imageViewCard_" + playerCard.getTypeCard() + "_" + idCard;
        //Log.d(ROUND, "idStringImageViewCard : " +idStringImageView);
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        //Log.d(ROUND, "cardViewTag : " + cardView.getTag());
        cardView.setImageResource(R.drawable.card_darkside);

    }


    public void showCard(Card cardPlayer, String idCard, Activity boardMascarade){
        String idStringImageView = "imageViewCard_" + cardPlayer.getTypeCard() + "_" + idCard;
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

            final TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
            textViewInstruction.setText("Vous souhaitez activer le pouvoir de votre carte.");

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
                        String idMainPlayer = Integer.toString(mainPlayer.getId());
                        //Log.d(ROUND, "good choice " + cardChosen + "  -  " + cardMainPlayer);
                        showCard(mainPlayer.getCard(), idMainPlayer, boardMascarade);

                        textViewInstruction.setText("Quelle mémoire ! ");

                        activePowerPlayer(mainPlayer);
                    } else {
                        textViewInstruction.setText("Mauvais choix ! Vous n'êtes pas un(e) " + cardChosen);
                        Log.d(ROUND, "bad choice " + cardChosen + "  -  " + cardMainPlayer);
                        AlertDialog.Builder builder = new AlertDialog.Builder(boardMascarade);
                        builder.setTitle("Vous vous êtes trompé de rôle");
                        builder.setMessage("Vous donnez 1 pièce d'or au Tribunal")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //boolean transaction = tribunal.getMoneyFromPlayer(mainPlayer);
                                        //Log.d(ROUND, "player has enough gold to give to the tribunal : " + transaction);
                                        // give 1 gold to the "Tribunal"
                                    }
                                });
                        builder.create();
                        builder.show();
                        textViewInstruction.setText("Le tribunal vous prend 1 pièce d'or.");
                    }
                    //mainPlayer.annoucementCard(cardChosen, tribunal);

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

    public void activePowerPlayer(Player playerConcerned){
        String typePower = playerConcerned.getCardType();
        if(typePower.equals("Sorciere")){
            this.activePowerSorciere(playerConcerned);
        }
        else if(typePower.equals("Espionne")){
            this.activePowerEspione(playerConcerned);
        }
        else if(typePower.equals("Eveque")){
            this.activePowerEveque(playerConcerned);
        }
        else if(typePower.equals("Fou")){
            this.activePowerFou(playerConcerned);
        }
        else if(typePower.equals("Inquisiteur")){
        }
        else if(typePower.equals("Juge")){
            this.activePowerJuge(playerConcerned);
        }
        else if(typePower.equals("Paysan")){
            this.activePowerPaysan(playerConcerned);
        }
        else if(typePower.equals("Reine")){

        }
        else if(typePower.equals("Roi")){
            this.activePowerRoi(playerConcerned);
        }
        else if(typePower.equals("Tricheur")){

        }
        else if(typePower.equals("Veuve")){

        }
    }

    public void activePowerPaysan(final Player playerConcerned) {
        TextView textViewInstruction = (TextView) boardMascarade.findViewById(R.id.textView_instructions);
        final Paysan paysanCardPlayerConcerned = (Paysan) playerConcerned.getCard();
        textViewInstruction.setText("Trouvez l'autre paysan !");

        AlertDialog.Builder builder = new AlertDialog.Builder(boardMascarade);
        builder.setTitle("Vous êtes un paysan");
        builder.setMessage("Voulez vous tenter de trouver l'autre paysan ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activateListenersCards(playerConcerned);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        paysanCardPlayerConcerned.activePower(false, playerConcerned, null);
                    }
                });
        builder.create();
        builder.show();

    }
    public void activePowerRoi(Player playerConcerned){

    }

    public void activePowerJuge(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        Juge jugeCardPlayerConcerned = (Juge)playerConcerned.getCard();
        int tribunalMoney = jugeCardPlayerConcerned.activePower(playerConcerned, tribunal);
        textViewInstruction.setText("Vous récupérez " + tribunalMoney + " du tribunal.");
    }

    public void activePowerEveque(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        Eveque evequeCardPlayerConcerned = (Eveque)playerConcerned.getCard();
        int nbMoneyRetrieved = evequeCardPlayerConcerned.activePower(playerConcerned, bank);
        String nameRichestPlayer = evequeCardPlayerConcerned.findRichestPlayers(bank).get(0).getName();
        textViewInstruction.setText("Vous prenez " + nbMoneyRetrieved + " à " + nameRichestPlayer);

    }

    public void activePowerFou(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        textViewInstruction.setText("Vous recevez une pièce d'or de la banque.\nChoisissez deux cartes que vous souhaiter échanger ou non.");

        this.activateListenersCards(playerConcerned);
    }

    public void activePowerEspione(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        textViewInstruction.setText("Choisissez la carte du joueur que vous souhaitez voir.");

        this.activateListenersCards(playerConcerned);


    }

    public void activePowerSorciere(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        textViewInstruction.setText("Choisissez la carte du joueur avec qui vous souhaitez échanger votre or.");

        this.activateListenersCards(playerConcerned);

        /*
        final ArrayList<Player> playerArrayList = bank.getListPlayers();
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
                Log.d(ROUND, "other player chosen : " + playerArrayList.get(which).getId());
                Sorciere cardPlayer = (Sorciere)playerConcerned.getCard();
                //cardPlayer.activePower(playerConcerned, playerArrayList.get(which));

            }
        });

        sorciereChoiceOpponentBuilder.create();
        sorciereChoiceOpponentBuilder.show();
        */
    }


    public void activateListenersCards(Player mainPlayer){

        ArrayList<Player> playerArrayList = bank.getListPlayers();

        for(int i = 0; i < playerArrayList.size() ; i++){
            Player player = playerArrayList.get(i);
            if(!player.isPlayer()){
                Card playerCardOpponent = playerArrayList.get(i).getCard();
                String idCard = Integer.toString(playerArrayList.get(i).getId());
                String idStringImageView = "imageViewCard_" + playerCardOpponent.getTypeCard() + "_" + idCard;
                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                cardView.setOnTouchListener(new OnTouchListenerCard(playerCardOpponent, cardView, mainPlayer));
            }

        }

        int nbPlayer = bank.getNbPlayers();
        if(nbPlayer < 6) {
            ArrayList<Card> cardsCenter = bank.getBankCardsCenter();
            for (int j = 0; j < cardsCenter.size(); j++) {
                Card cardCenter = cardsCenter.get(j);
                String idCard = "_center_" + j + 1;
                String idStringImageView = "imageViewCard_" + cardCenter.getTypeCard() + "_" + idCard;

                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                cardView.setOnTouchListener(new OnTouchListenerCard(cardCenter, cardView, mainPlayer));
            }
        }


    }

    class OnTouchListenerCard implements View.OnTouchListener {

        private final Card cardOpponent;
        private final ImageView imageCard;
        private final Player playerConcerned;

        OnTouchListenerCard(Card cardOpponent, ImageView imageCard, Player playerConcerned){
            this.cardOpponent = cardOpponent;
            this.imageCard = imageCard;
            this.playerConcerned = playerConcerned;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                String typeCardPlayerConcerned = playerConcerned.getCardType();

                TextView textViewInstructions = (TextView) boardMascarade.findViewById(R.id.textView_instructions);

                Log.d(ROUND, "playerConcernedCard : " + typeCardPlayerConcerned + "  card : " + cardOpponent.getTypeCard());

                if (typeCardPlayerConcerned.equals("Sorciere")) {
                    Player opponentPlayer = bank.getPlayerWithCard(cardOpponent.getTypeCard());
                    textViewInstructions.setText("Vous avez sélectionné le " + opponentPlayer.getName() + " pour échanger votre or.");
                    Sorciere playerConcernedCard = (Sorciere) playerConcerned.getCard();
                    playerConcernedCard.activePower(playerConcerned, opponentPlayer);
                } else if (typeCardPlayerConcerned.equals("Espionne")) {
                    textViewInstructions.setText("Carte espionne");
                } else if (typeCardPlayerConcerned.equals("Eveque")) {
                    textViewInstructions.setText("carte eveque");
                } else if (typeCardPlayerConcerned.equals("Fou")) {
                    textViewInstructions.setText("carte fou");
                } else if (typeCardPlayerConcerned.equals("Inquisiteur")) {
                    textViewInstructions.setText("carte inquisteur");
                } else if (typeCardPlayerConcerned.equals("Juge")) {
                    textViewInstructions.setText("carte juge");
                } else if (typeCardPlayerConcerned.equals("Paysan")) {
                    textViewInstructions.setText("carte paysan");

                    if (cardOpponent.getTypeCard().equals("Paysan")) {
                        Player partnerPlayer = bank.getPlayerWithCard(cardOpponent.getTypeCard());
                        String idStringImageView = "imageViewCard_" + partnerPlayer.getTypeCard() + "_" + partnerPlayer.getId();
                        ImageView paysanPartnerImageView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                        paysanPartnerImageView.setImageResource(R.drawable.paysan_card);

                        Paysan playerConcernedCard = (Paysan) playerConcerned.getCard();
                        playerConcernedCard.activePower(true, playerConcerned, partnerPlayer);
                    }


                } else if (typeCardPlayerConcerned.equals("Reine")) {
                    textViewInstructions.setText("carte reine");
                } else if (typeCardPlayerConcerned.equals("Roi")) {
                    textViewInstructions.setText("carte roi");
                } else if (typeCardPlayerConcerned.equals("Tricheur")) {
                    textViewInstructions.setText("carte tricheur");
                } else if (typeCardPlayerConcerned.equals("Veuve")) {
                    textViewInstructions.setText("carte veuve");
                }

            }
            return true;
        }
    }


}
