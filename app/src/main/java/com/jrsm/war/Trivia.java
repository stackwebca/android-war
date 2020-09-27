package com.jrsm.war;

import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.jrsm.blackjack.R;

import java.util.ArrayList;
import java.util.List;

import data.AnswerListAsyncResponse;
import data.QuestionBank;
import model.Question;

public class Trivia extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextview;
    private TextView questionCounterTextview;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;
    private SoundPool soundPool;
    private int sound1, sound2;
    public static int MAX_STREAMS = 1;
    public static int SOUND_PRIORITY = 1;
    public static int SOUND_QUALITY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.icon_logo);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        falseButton = findViewById(R.id.false_button);
        trueButton = findViewById(R.id.true_button);

        questionCounterTextview = findViewById(R.id.counter_text);
        questionTextview = findViewById(R.id.question_textview);

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


        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

            questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
//                Log.d("Inside", "processFinished: " + questionArrayList);
            }
        });

//        Log.d("Main", "onCreate: " + questionList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.prev_button:
//                currentQuestionIndex = (currentQuestionIndex + (int)(Math.random() * 913) + 1) % questionList.size();
//                updateQuestion();
//                break;
            case R.id.next_button:
                currentQuestionIndex = (currentQuestionIndex + (int)(Math.random() * 913) - 1) % questionList.size();
                updateQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);

                break;
            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;
        }

    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
        int toastMessageId = 0;

        if (userChooseCorrect == answerIsTrue) {
            fadeView();
            toastMessageId = R.string.correct_answer;
            pLoseSound();
        } else {
            updateQuestion();
            shakeAnimation();
            toastMessageId = R.string.wrong_answer;
            pWinSound();

        }
        Toast.makeText(Trivia.this, toastMessageId,
                Toast.LENGTH_SHORT)
                .show();
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextview.setText(question);
        questionCounterTextview.setText(currentQuestionIndex + " / " + questionList.size()); // 0 / 234

    }

    private void fadeView() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(Trivia.this,
                R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void quit(View v) {
        // TODO Auto-generated method stub
        finish();
        System.exit(0);
    }

    public void pLoseSound() {soundPool.play(sound2, 1, 1, 1, 0, 1);
    }

    public void pWinSound() {
        soundPool.play(sound1, 1, 1, 1, 0, 1);
    }
}


