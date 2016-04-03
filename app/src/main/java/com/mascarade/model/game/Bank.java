package com.mascarade.model.game;

import android.util.Log;

import com.mascarade.model.cards.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by melanie on 13/03/16.
 */
public class Bank {
    private static final String MASCARADE = "BANKCLASS";

    private int nbMoney = 1000;
    private ArrayList<Card> bankCardsListStart = new ArrayList<>();
    private ArrayList<Card> bankCardsCenter = new ArrayList<>();
    private ArrayList<Player> listPlayers = new ArrayList<>();

    private int nbPlayers = 0;

    private Player winner;

    private int countRound = 0;

    public Bank(int nbPlayers) {

        this.nbPlayers = nbPlayers;
    }

    public void initialiseCardsBank() {

        ArrayList<Card> listCardsGame = new ArrayList<>();

        if (nbPlayers < 4) {
            System.err.println("Le nombre de jouers doit être au moins 4.");
        } else if (nbPlayers > 13) {
            System.err.println("Le nombre de jouers maximal est 13.");
        }

        Juge juge = new Juge();
        Eveque eveque = new Eveque();
        Roi roi = new Roi();
        Reine reine = new Reine();

        listCardsGame.add(juge);
        listCardsGame.add(eveque);
        listCardsGame.add(roi);
        listCardsGame.add(reine);

        if (nbPlayers >= 8) {
            Fou fou = new Fou();
            listCardsGame.add(fou);
        }
        if (nbPlayers == 4 || nbPlayers == 7 || nbPlayers == 13) {
            Voleur voleur = new Voleur();
            listCardsGame.add(voleur);
        }
        if (nbPlayers >= 5) {
            Sorciere sorciere = new Sorciere();
            listCardsGame.add(sorciere);
        }
        if (nbPlayers == 7 || nbPlayers >= 10) {
            Espionne espionne = new Espionne();
            listCardsGame.add(espionne);
        }
        if (nbPlayers >= 8) {
            Paysan paysanFirst = new Paysan();
            Paysan paysanSecond = new Paysan();

            listCardsGame.add(paysanFirst);
            listCardsGame.add(paysanSecond);
        }
        if (nbPlayers != 7 && nbPlayers != 8) {
            Tricheur tricheur = new Tricheur();
            listCardsGame.add(tricheur);
        }
        if (nbPlayers >= 11) {
            Inquisiteur inquisiteur = new Inquisiteur();
            listCardsGame.add(inquisiteur);
        }
        if (nbPlayers >= 12) {
            Veuve veuve = new Veuve();
            listCardsGame.add(veuve);
        }

        this.setBankCardsListStart(listCardsGame);
    }

    public void initialiseNbPlayers(String pseudoMainPlayer) {
        int index = 0;
        ArrayList<Player> playersList = new ArrayList<>();

        while (index < nbPlayers) {
            Player player = new Player(6, new Card(), index);
            player.setName("Player " + index);
            playersList.add(player);
            index++;
        }

        this.setListPlayers(playersList);

        //this player will be the main player
        Player randomMainPlayer = this.getRandomPlayer();
        randomMainPlayer.setName(pseudoMainPlayer);
        randomMainPlayer.setIsPlayer(true);
    }

    public void distributionCards() {
        ArrayList<Player> listPlayers = this.getListPlayers();
        ArrayList<Card> cardsList = this.getBankCardsListStart();
        ArrayList<Card> cardsListCenter = new ArrayList<>();//this.getBankCardsListStart();
        ArrayList<Card> cardsListRemove = new ArrayList<>();

        ArrayList<Integer> cardsDone = new ArrayList<>();
        Log.d(MASCARADE, "nb joueur : " + listPlayers.size());

        for (int p = 0; p < listPlayers.size(); p++) {
            Player player = listPlayers.get(p);
            Random randomIndex = new Random();
            int indexRandom = randomIndex.nextInt(nbPlayers);
            while (cardsDone.contains(indexRandom)) {
                indexRandom = randomIndex.nextInt(nbPlayers);
            }
            Log.d(MASCARADE, "random : " + indexRandom + "  cardsDone  " + cardsDone.toString());
            if (!cardsDone.contains(indexRandom)) {
                Card card = cardsList.get(indexRandom);
                cardsDone.add(indexRandom);
                player.setCard(card);
                if (nbPlayers < 6) {
                    cardsListRemove.add(card);
                }
            }

            Log.d(MASCARADE, "Joueur " + player.getId() + " obtient la carte " + player.getTypeCard() + " " + indexRandom);
        }

        if (nbPlayers < 6) {
            Log.d(MASCARADE, "Il y a " + nbPlayers + " joueurs donc le(s) carte(s) suivante(s) va/vont au centre : ");
            for (int i = 0; i < cardsList.size(); i++) {
                Card card = cardsList.get(i);

                if (!cardsListRemove.contains(card)) {
                    cardsListCenter.add(card);
                }
            }

            this.setBankCardsCenter(cardsListCenter);
            for (int c = 0; c < this.getBankCardsCenter().size(); c++) {
                Log.d(MASCARADE, " -> " + this.getBankCardsCenter().get(c).getTypeCard());
            }
        }

    }


    public ArrayList<Player> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(ArrayList<Player> listPlayers) {
        this.listPlayers = listPlayers;
    }

    public ArrayList<Card> getBankCardsCenter() {
        return bankCardsCenter;
    }

    public void setBankCardsCenter(ArrayList<Card> bankCardsCenter) {
        this.bankCardsCenter = bankCardsCenter;
    }

    public int getNbMoney() {
        return nbMoney;
    }

    public void setNbMoney(int nbMoney) {
        this.nbMoney = nbMoney;
    }

    public ArrayList<Card> getBankCardsListStart() {
        return bankCardsListStart;
    }

    public void setBankCardsListStart(ArrayList<Card> bankCardsListStart) {
        this.bankCardsListStart = bankCardsListStart;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public Player getPlayerWithCard(String typeCardConcerned) {
        ArrayList<Player> listPlayers = this.getListPlayers();
        Player playerConcerned = null;
        boolean playerFound = false;
        for (int i = 0; i < listPlayers.size(); i++) {
            if (!playerFound) {
                Player player = listPlayers.get(i);
                String cardPlayer = player.getTypeCard();
                if (cardPlayer.equals(typeCardConcerned)) {
                    playerConcerned = player;
                    playerFound = true;
                }
            }
        }

        return playerConcerned;
    }

    public Player getRandomPlayer() {
        Random randomIdPlayer = new Random();
        int indexRandom = randomIdPlayer.nextInt(nbPlayers);
        Player randomPlayer = this.getListPlayers().get(indexRandom);

        return randomPlayer;
    }

    public Player getMainPlayer(){
        ArrayList<Player> playerArrayList = this.getListPlayers();
        Player mainPlayer = null;
        for(int i = 0 ; i < playerArrayList.size() ; i++){
            Player player = playerArrayList.get(i);
            boolean isPlayer = player.isPlayer();
            if(isPlayer){
                mainPlayer = player;
            }
        }

        return mainPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getCountRound() {
        return countRound;
    }

    public void setCountRound(int countRound) {
        this.countRound = countRound;
    }
}
