package com.jrsm.war;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jrsm.blackjack.R;


import java.util.Random;

public class playWar extends AppCompatActivity {

    ImageView playercard1, playercard2, androidcard1, androidcard2, arrow;
    TextView pscore1, ascore1, rndsText;
    int war, roundNumber, pscore, ascore, pround, around;

    private SoundPool soundPool;
    private int sound1, sound2, sound3, sound4;

    ImageView gameLogo, triviaImg;
    Animation myAnim, pwin, awin;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.icon_logo);

        setContentView(R.layout.activity_play_war);
        playercard1 = findViewById(R.id.pCard1);
        playercard2 = findViewById(R.id.pCard2);
        androidcard1 = findViewById(R.id.aCard2);
        androidcard2 = findViewById(R.id.aCard1);
        arrow = findViewById(R.id.arrow);
        pscore1 = findViewById(R.id.pscore);
        ascore1 = findViewById(R.id.ascore);
        gameLogo = findViewById(R.id.gameLogo);
        triviaImg = findViewById(R.id.triviaBonus);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.itswar);
        pwin = AnimationUtils.loadAnimation(this, R.anim.awin);
        awin = AnimationUtils.loadAnimation(this, R.anim.pwin);

        myAnim.reset();
        gameLogo.clearAnimation();
        gameLogo.startAnimation(myAnim);


        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(audioAttributes)
                .build();

        sound1 = soundPool.load(this, R.raw.ulose, 1);
        sound2 = soundPool.load(this, R.raw.uwin, 1);
        sound3 = soundPool.load(this, R.raw.itswar, 1);

    }


    public int getPscore() {
        return pscore;
    }

    public void setPscore(int pscore) {
        this.pscore = pscore;
    }

    public int getAscore() {
        return ascore;
    }

    public void setAscore(int ascore) {
        this.ascore = ascore;
    }

    public int getPround() {
        return pround;
    }

    public void setPround(int pround) {
        this.pround = pround;
    }

    public int getAround() {
        return around;
    }

    public void setAround(int around) {
        this.around = around;
    }

    public int getWar() {
        return war;
    }

    public void setWar(int war) {
        this.war = war;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void tapDeck(View v) {
        ascore1.clearAnimation();
        pscore1.clearAnimation();
        playerDraw();
        androidRun();
        alertScore();

        roundNumber++;
        if (roundNumber >= 10) {
            triviaImg.setVisibility(View.VISIBLE);
        } else {
            triviaImg.setVisibility(View.INVISIBLE);
        }

    }


    public void warAndroidRun() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                warAndroidDraw();
            }
        }, 1000);
    }

    public void androidRun() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                androidDraw();
            }
        }, 1000);
    }

    public void war() {
        this.getWar();
        this.setWar(1);


        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                itsWarSound();
            }
        }, 3000);
        warAndroidRun();
        itsWarSound();

        myAnim.reset();
        gameLogo.clearAnimation();
        gameLogo.startAnimation(myAnim);

        Toast toast = Toast.makeText(this, "War! Bonus 2 x Pts!" + war, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                warPlayerDraw();
            }
        }, 3000);
        warAndroidRun();
    }

    public void pLoseSound() {
        soundPool.play(sound2, 1, 1, 1, 0, 1);
    }

    public void pWinSound() {
        soundPool.play(sound1, 1, 1, 1, 0, 1);
    }

    public void itsWarSound() {
        soundPool.play(sound3, 1, 1, 1, 0, 1);
    }

    public void checkWar() {
        if (pround == around) {
            war();
        }
        this.getWar();
        this.setWar(0);
        noWarRound();
    }

    public void warPlayerBonus() {
        pWinSound();
        pscore1.startAnimation(pwin);

        Toast toast = Toast.makeText(this, "Player Wins " + (pround + pround) + "pts!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void warAndroidBonus() {
        ascore1.startAnimation(awin);

        pLoseSound();
        Toast toast = Toast.makeText(this, "Android Wins " + (around + around) + "pts!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void warRound() {
        warPlayerDraw();
        warAndroidDraw();

        if (pround > around) {
            pscore += pround + pround;
            pscore1.setText(Integer.toString(pscore));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    warPlayerBonus();
                }
            }, 3000);
            warAndroidRun();
        } else if (around > pround) {
            ascore += around + around;
            ascore1.setText(Integer.toString(ascore));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    warAndroidBonus();
                }
            }, 3000);
            warAndroidRun();
        }
        war = 0;
    }

    public void noWarRound() {
        if (pround > around) {
            pscore += pround;
            pscore1.setText(Integer.toString(pscore));
            pscore1.startAnimation(pwin);

            Toast toast = Toast.makeText(this, "Player Wins " + pround + "pts!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (around > pround) {
            ascore += around;
            ascore1.startAnimation(awin);

            ascore1.setText(Integer.toString(ascore));
            Toast toast = Toast.makeText(this, "Android Wins " + around + "pts!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void playerDraw() {
        pround = 0;
        getPround();
        Random rand = new Random();
        String suits = "";
        int rand1 = rand.nextInt(4) + 1;
        if (rand1 == 1) {
            suits = "c";
        } else if (rand1 == 2) {
            suits = "d";
        } else if (rand1 == 3) {
            suits = "h";
        } else
            suits = "s";

        int rand2 = rand.nextInt(13) + 1;
        String dName = suits + rand2;
        playercard2.setVisibility(View.VISIBLE);
        int id = getResources().getIdentifier(dName, "drawable", getPackageName());
        playercard2.setImageResource(id);
        pround = rand2;
    }

    public void androidDraw() {
        around = 0;
        getAround();
        Random rand = new Random();
        String suits = "";
        int rand1 = rand.nextInt(4) + 1;
        if (rand1 == 1) {
            suits = "c";
        } else if (rand1 == 2) {
            suits = "d";
        } else if (rand1 == 3) {
            suits = "h";
        } else
            suits = "s";

        int rand2 = rand.nextInt(13) + 1;
        String dName = suits + rand2;
        androidcard1.setVisibility(View.VISIBLE);
        int id = getResources().getIdentifier(dName, "drawable", getPackageName());
        androidcard1.setImageResource(id);
        around = rand2;

        checkWar();
    }

    public void warPlayerDraw() {
        pround = 0;
        getPround();
        Random rand = new Random();
        String suits = "";
        int rand1 = rand.nextInt(4) + 1;
        if (rand1 == 1) {
            suits = "c";
        } else if (rand1 == 2) {
            suits = "d";
        } else if (rand1 == 3) {
            suits = "h";
        } else
            suits = "s";

        int rand2 = rand.nextInt(13) + 1;
        String dName = suits + rand2;
        playercard2.setVisibility(View.VISIBLE);
        int id = getResources().getIdentifier(dName, "drawable", getPackageName());
        playercard2.setImageResource(id);
        pround = (rand2 + rand2);
    }

    public void warAndroidDraw() {
        around = 0;
        getAround();
        Random rand = new Random();
        String suits = "";
        int rand1 = rand.nextInt(4) + 1;
        if (rand1 == 1) {
            suits = "c";
        } else if (rand1 == 2) {
            suits = "d";
        } else if (rand1 == 3) {
            suits = "h";
        } else
            suits = "s";

        int rand2 = rand.nextInt(13) + 1;
        String dName = suits + rand2;
        androidcard1.setVisibility(View.VISIBLE);
        int id = getResources().getIdentifier(dName, "drawable", getPackageName());
        androidcard1.setImageResource(id);
        around = (rand2 + rand2);

        checkWar();
    }

    public void playTrivia() {


    }

    public void gameInfo(View v) {
        Intent i = new Intent(getApplicationContext(), aboutPage.class);
        startActivity(i);

    }

    public void openTrivia(View v) {
        Intent i = new Intent(getApplicationContext(), Trivia.class);
        startActivity(i);
    }

    public void surrender(View v) {
        // TODO Auto-generated method stub
        finish();
        System.exit(0);
    }

    public void alertScore() {
        if ((pscore - 20) > ascore) pscore1.setTextColor(Color.GREEN);
        if ((pscore + 20) < ascore) pscore1.setTextColor(Color.rgb(255, 165, 0));
        if ((pscore + 50) < ascore) pscore1.setTextColor(Color.RED);
        pscore1.setText(Integer.toString(pscore));
    }

}