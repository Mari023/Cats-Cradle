package de.mari_023.android.catscradle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int questions = 93;
    public static SharedPreferences sharedpreferences;
    Storage storage;
    private Button answerA, answerB, answerC, answerD;
    private TextView question;
    private TextView score;
    private TextView highscore;
    private Answer correct;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences("CatsCradle", Context.MODE_PRIVATE);
        storage = Storage.getStorage();

        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        highscore = findViewById(R.id.highscore);

        question.setTextSize(22);
        answerA.setTextSize(20);
        answerB.setTextSize(20);
        answerC.setTextSize(20);
        answerD.setTextSize(20);

        setup(storage.getQuestionNumber());

        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onClick(answerA, Answer.ANSWERA);
            }
        });
        answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onClick(answerB, Answer.ANSWERB);
            }
        });
        answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onClick(answerC, Answer.ANSWERC);
            }
        });
        answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onClick(answerD, Answer.ANSWERD);
            }
        });
    }

    private void onClick(Button button, Answer answer) {
        if(done) setup(storage.getQuestionNumber());
        else if(correct == answer) {
            button.setBackgroundColor(Color.GREEN);
            done = true;
            storage.resetQuestionNumber();
            storage.increaseScore();
        } else {
            button.setBackgroundColor(Color.RED);
            storage.resetScore();
        }
    }

    private void setup(int questionNumber) {
        List<Integer> answers = new ArrayList<>();
        answers.add(getResources().getIdentifier("answer" + questionNumber + "a", "string", getPackageName()));
        answers.add(getResources().getIdentifier("answer" + questionNumber + "b", "string", getPackageName()));
        answers.add(getResources().getIdentifier("answer" + questionNumber + "c", "string", getPackageName()));
        answers.add(getResources().getIdentifier("answer" + questionNumber + "d", "string", getPackageName()));
        Collections.shuffle(answers);
        answerA.setText(answers.get(0));
        answerB.setText(answers.get(1));
        answerC.setText(answers.get(2));
        answerD.setText(answers.get(3));
        question.setText(getResources().getIdentifier("question" + questionNumber, "string", getPackageName()));
        correct = Answer.valueOf(answers.indexOf(getResources().getIdentifier("answer" + questionNumber + "a", "string", getPackageName())));

        done = false;
        answerA.setBackgroundColor(Color.LTGRAY);
        answerB.setBackgroundColor(Color.LTGRAY);
        answerC.setBackgroundColor(Color.LTGRAY);
        answerD.setBackgroundColor(Color.LTGRAY);

        score.setText("" + storage.getScore());
        highscore.setText("" + storage.getHighScore());
    }
}
