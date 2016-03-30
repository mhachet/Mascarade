package com.mascarade.model.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mascarade.R;
import com.mascarade.model.adapter.ListItem;
import com.mascarade.model.adapter.RolesAdapter;
import com.mascarade.model.cards.Card;
import com.mascarade.model.cards.Espionne;
import com.mascarade.model.cards.Fou;
import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;
import com.mascarade.model.game.Tribunal;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PlateauMascarade extends Activity {

    private static final String PLATEAU = "PLATEAU";
    private String pseudo = "";
    private  int nbPlayers = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plateau_mascarade);

        Intent plateau = getIntent();
        int nbPlayers = Integer.parseInt(plateau.getStringExtra("nbPlayers"));
        String pseudo = plateau.getStringExtra("pseudo");
        Bank newGame = new Bank(nbPlayers);
        newGame.initialiseCardsBank();
        newGame.initialiseNbPlayers();

        newGame.distributionCards();

        Tribunal tribunal = new Tribunal(newGame.getBankCardsListStart());
        this.drawRolesInGame(newGame);

        //boolean exchange = true;
        //this.espionnePower(newGame, exchange);
        //this.evequePower(newGame);
        //this.fouPower(newGame);

    }

    public void drawRolesInGame(Bank bank){
        List<Card> cardArrayListInGame = bank.getBankCardsListStart();
        List listTypeCards = new ArrayList<>();
        //listTypeCards.add(imageView);
        ArrayList<ListItem> myList = new ArrayList<>();
        ListView listViewCardsLabel = (ListView) findViewById(R.id.listView_roles_game);
        //Log.d(PLATEAU, "image id : " + R.drawable.roi_label + "  " + R.drawable.juge_label + " " + R.drawable.reine_label + " " + R.drawable.eveque_label);

        String [] names = new String[cardArrayListInGame.size()];
        int[] images = new int [cardArrayListInGame.size()];
        boolean paysan = false;
        for(int i = 0 ; i < cardArrayListInGame.size(); i++){
            Card card = cardArrayListInGame.get(i);
            String cardType = cardArrayListInGame.get(i).getTypeCard();

            int idImage =this.getIdImageFromCard(card);

            if(idImage == 2130837584 && paysan == false) {
                names[i] = cardType;
                images[i] = idImage;
                paysan = true;
            }
            else if(idImage != 2130837584){
                names[i] = cardType;
                images[i] = idImage;
            }
            
            Log.d(PLATEAU, "id : " + images[i] + " -- " + cardType.toLowerCase() + "_label");
            ListItem item = new ListItem(images[i], names[i]);
            item.setImageId(images[i]);
            item.setNameImage(names[i]);
            myList.add(item);
        }

        //int[] images = { R.drawable.roi_label, R.drawable.juge_label,
                //R.drawable.reine_label, R.drawable.eveque_label };

        //String [] names = {"Roi", "Juge", "Reine", "Eveque"};

/*
        for (int i = 0; i < images.length; i++) {
            ListItem item = new ListItem(images[i], names[i]);
            item.setImageId(images[i]);
            item.setNameImage(names[i]);
            Log.d(PLATEAU, names[i] + " => id : " + images[i]);
            myList.add(item);
        }
*/
        RolesAdapter adapter = new RolesAdapter(this, myList);
        listViewCardsLabel.setAdapter(adapter);
        listViewCardsLabel.setOnItemClickListener(adapter);



    }

    public int getIdImageFromCard(Card card){

        String cardType = card.getTypeCard();

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

    public void fouPower(Bank game) {
        boolean changeCards = true;
        Player playerFou = game.getPlayerWithCard("Fou");
        Fou fou = (Fou) playerFou.getCard();
        Player firstPlayer = game.getRandomPlayer();
        Player secondPlayer = game.getRandomPlayer();
        while (firstPlayer.equals(playerFou)) {
            firstPlayer = game.getRandomPlayer();
        }
        while (secondPlayer.equals(playerFou) || secondPlayer.equals(firstPlayer)) {
            secondPlayer = game.getRandomPlayer();
        }
        fou.activePower(playerFou, firstPlayer, secondPlayer, changeCards);

    }

    public boolean espionnePower(Bank game, boolean exchange) {
        boolean exchangeDone = false;
        Player playerEspion = game.getPlayerWithCard("Espionne");
        Espionne espion = (Espionne) playerEspion.getCard();
        Log.d(PLATEAU, "espion est : " + playerEspion.getId() + "  " + playerEspion.getTypeCard());
        int idPlayerEspion = playerEspion.getId();
        Player opponentPlayer = null;
        if (idPlayerEspion < game.getListPlayers().size() - 1) {
            opponentPlayer = game.getListPlayers().get(idPlayerEspion + 1);
        } else {
            opponentPlayer = game.getListPlayers().get(idPlayerEspion - 1);
        }
        Log.d(PLATEAU, "playerOpponent : " + opponentPlayer.getId() + "  " + opponentPlayer.getTypeCard());
        espion.activePower(playerEspion, opponentPlayer, exchange);
        exchangeDone = true;


        return exchangeDone;
    }

    public int generateNbPlayers() {
        Random random = new Random();
        int nbPlayers = random.nextInt((13 - 4) + 1) + 4;

        return nbPlayers;
    }

}
