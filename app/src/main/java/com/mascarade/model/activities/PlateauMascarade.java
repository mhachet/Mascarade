package com.mascarade.model.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mascarade.R;
import com.mascarade.model.cards.Card;
import com.mascarade.model.cards.Espionne;
import com.mascarade.model.cards.Fou;
import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;
import com.mascarade.model.game.Tribunal;

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
        //this.drawRolesInGame(newGame);

        //boolean exchange = true;
        //this.espionnePower(newGame, exchange);
        //this.evequePower(newGame);
        //this.fouPower(newGame);

        /*
        TextView textView = new TextView(this);
        textView.setText("Le pseudo est : " + pseudo + "  et le nombre de joueurs est : " + nbPlayers + ".\n" +
                "Les cartes en jeu sont : " + newGame.getBankCardsListStart());
        Log.d(PLATEAU, "Le pseudo est : " + pseudo + "  et le nombre de joueurs est : " + nbPlayers + ".\n" +
                "Les cartes en jeu sont : " + newGame.getBankCardsListStart());
        setContentView(textView);
        */
    }
/*
    public void drawRolesInGame(Bank bank){
        Spinner spinnerCardsInGame = (Spinner)findViewById(R.id.spinner_roles_game);
        List<Card> cardArrayListInGame = bank.getBankCardsListStart();
        List listTypeCards = new ArrayList<>();
        for(int i = 0 ; i < cardArrayListInGame.size(); i++){
            String cardType = cardArrayListInGame.get(i).getTypeCard();
            listTypeCards.add(cardType);
        }
        Log.d(PLATEAU, "Les cartes en jeu sont : " + listTypeCards);
        ArrayAdapter adapterRolesInGame = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listTypeCards);
        adapterRolesInGame.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCardsInGame.setAdapter(adapterRolesInGame);


    }

    */
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
