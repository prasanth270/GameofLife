package com.example.rahul.gameoflife;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button playButton;
    private Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        playButton = (Button) findViewById(R.id.playButton);
        aboutButton = (Button) findViewById(R.id.aboutButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, GridActivity.class);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAbout();
            }
        });
    }

    private void showAbout() {
        final AlertDialog alertDialog = new AlertDialog.Builder(WelcomeActivity.this).create();
        alertDialog.setTitle("About");
        alertDialog.setMessage("1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation " + "\n" +
                "2. Any live cell with two or three live neighbours lives on to the next generation." + "\n" +
                "3. Any live cell with more than three live neighbours dies, as if by overpopulation." + "\n" +
                "4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction." + "\n");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}