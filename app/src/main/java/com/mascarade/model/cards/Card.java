package com.mascarade.model.cards;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.mascarade.R;
import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public abstract class Card {

    protected Activity boardMascarade;

    protected Player concernedPlayer;
    protected boolean visibleAll = false;
    protected boolean visiblePlayer = false;
    private final String CARD = "CARD";

    public Card() {

    }

    /**
     * Hide card's player
     *
     * @param idPlayer
     * @param boardMascarade
     */
    public void hideCard(String idPlayer, Activity boardMascarade){
        String idStringImageView = "imageViewCard_" + idPlayer;
        Log.d(CARD, "idStringImageView : " + idStringImageView);
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        cardView.setImageResource(R.drawable.card_darkside);

    }

    /**
     *
     * Show player's card
     *
     * @param idCard
     * @param boardMascarade
     */
    public void showCard(String idCard, Activity boardMascarade){
        String idStringImageView = "imageViewCard_" + idCard;
        Log.d(CARD, "idStringImageView Card : " + idStringImageView);
        int idCardImage = this.getIdCardImageFromCard();
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        cardView.setImageResource(idCardImage);
        this.setVisiblePlayer(true);
    }

    public Bitmap getLabelCard(String imageLabelPath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imageLabelPath, options);

        return bitmap;
        //selected_photo.setImageBitmap(bitmap);
    }

    public int getIdImageLabelFromCard(){

        String cardType = this.getTypeCard();

        int idImage = 0;

        if(cardType.equals("Juge")) {
            idImage = R.drawable.juge_label;
        }
        else if(cardType.equals("Espionne")) {
            idImage = R.drawable.espionne_label;
        }
        else if(cardType.equals("Eveque")) {
            idImage = R.drawable.eveque_label;
        }
        else if(cardType.equals("Fou")) {
            idImage = R.drawable.fou_label;
        }
        else if(cardType.equals("Inquisiteur")) {
            idImage = R.drawable.inquisiteur_label;
        }
        else if(cardType.equals("Paysan")) {
            idImage = R.drawable.paysans_label;
        }
        else if(cardType.equals("Reine")) {
            idImage = R.drawable.reine_label;
        }
        else if(cardType.equals("Roi")) {
            idImage = R.drawable.roi_label;
        }
        else if(cardType.equals("Sorciere")) {
            idImage = R.drawable.sorciere_label;
        }
        else if(cardType.equals("Tricheur")) {
            idImage = R.drawable.tricheur_label;
        }
        else if(cardType.equals("Veuve")) {
            idImage = R.drawable.veuve_label;
        }
        else if(cardType.equals("Voleur")) {
            idImage = R.drawable.voleur_label;
        }

        return idImage;
    }

    public int getIdCardImageFromCard(){

        String cardType = this.getTypeCard();

        int idImage = 0;

        if(cardType.equals("Juge")) {
            idImage = R.drawable.juge_card;
        }
        else if(cardType.equals("Espionne")) {
            idImage = R.drawable.espionne_card;
        }
        else if(cardType.equals("Eveque")) {
            idImage = R.drawable.eveque_card;
        }
        else if(cardType.equals("Fou")) {
            idImage = R.drawable.fou_card;
        }
        else if(cardType.equals("Inquisiteur")) {
            idImage = R.drawable.inquisiteur_card;
        }
        else if(cardType.equals("Paysan")) {
            idImage = R.drawable.paysan_card;
        }
        else if(cardType.equals("Reine")) {
            idImage = R.drawable.reine_card;
        }
        else if(cardType.equals("Roi")) {
            idImage = R.drawable.roi_card;
        }
        else if(cardType.equals("Sorciere")) {
            idImage = R.drawable.sorciere_card;
        }
        else if(cardType.equals("Tricheur")) {
            idImage = R.drawable.tricheur_card;
        }
        else if(cardType.equals("Veuve")) {
            idImage = R.drawable.veuve_card;
        }
        else if(cardType.equals("Voleur")) {
            idImage = R.drawable.voleur_card;
        }

        return idImage;
    }

    public Activity getBoardMascarade() {
        return boardMascarade;
    }

    public void setBoardMascarade(Activity boardMascarade) {
        this.boardMascarade = boardMascarade;
    }

    public Player getPlayer() {
        return concernedPlayer;
    }

    public void setPlayer(Player player) {
        this.concernedPlayer = player;
    }

    public void exchangeCards(Player playerOne, Player playerTwo) {

    }

    public String getTypeCard() {
        return this.getClass().getSimpleName();
    }

    public boolean isVisibleAll() {
        return visibleAll;
    }

    public void setVisibleAll(boolean visibleAll) {
        this.visibleAll = visibleAll;
    }

    public boolean isVisiblePlayer() {
        return visiblePlayer;
    }

    public void setVisiblePlayer(boolean visiblePlayer) {
        this.visiblePlayer = visiblePlayer;
    }

}
