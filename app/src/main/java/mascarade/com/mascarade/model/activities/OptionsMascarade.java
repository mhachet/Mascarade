package com.model.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mascarade.activities.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melanie on 3/20/16.
 */
public class OptionsMascarade extends Activity {

    private static final String OPTIONS = "OPTIONS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(OPTIONS, "Start options");
        setContentView(R.layout.activity_options_mascarade);
        this.createSpinnerNbPlayers();

        Button start = (Button)findViewById(R.id.button_start);
        start.setOnClickListener(new ButtonOnClickListener(start));
    }

    public void createSpinnerNbPlayers(){
        Spinner spinnerNbPlayers = (Spinner) findViewById(R.id.spinner_nb_players);
        List nbPlayers = new ArrayList();
        nbPlayers.add("4");
        nbPlayers.add("5");
        nbPlayers.add("6");
        nbPlayers.add("7");
        nbPlayers.add("8");
        nbPlayers.add("9");
        nbPlayers.add("10");
        nbPlayers.add("11");
        nbPlayers.add("12");
        nbPlayers.add("13");

        ArrayAdapter adapterNbPlayers = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nbPlayers);
        adapterNbPlayers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNbPlayers.setAdapter(adapterNbPlayers);

    }

    class ButtonOnClickListener implements View.OnClickListener{
        private final Button button;

        ButtonOnClickListener(Button button) {
            this.button = button;
        }
        @Override public void onClick(View v) {
            Spinner spinnerNbPlayers = (Spinner) findViewById(R.id.spinner_nb_players);
            String nbPlayers = spinnerNbPlayers.getSelectedItem().toString();
            EditText editTextPseudo = (EditText)findViewById(R.id.editText_pseudo);
            String pseudo = editTextPseudo.getText().toString();
            Intent startGame = new Intent(OptionsMascarade.this, PlateauMascarade.class);
            Log.d(OPTIONS, "Le nombre de joueurs sélectionnés est : " + nbPlayers);
            Log.d(OPTIONS, "Le pseudo est : " + pseudo);

            Bundle b = new Bundle();
            startGame.putExtra("pseudo", pseudo);
            startGame.putExtra("nbPlayers", nbPlayers);
            startActivity(startGame);
        }
    }
}
