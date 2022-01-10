package de.mari_023.android.catscradle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int questions = 120;
    public static SharedPreferences sharedpreferences;
    Storage storage;
    private Button answerA, answerB, answerC, answerD;
    private TextView question;
    private TextView score;
    private TextView highscore;
    private Answer correct;
    private boolean done;
    private Tutorial tutorial;
    private TextView tutorialLower, tutorialUpper;
    private ImageView arrow0, arrow1A, arrow1B, arrow1C, arrow1D, arrow4, arrow5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences("CatsCradle", Context.MODE_PRIVATE);
        storage = Storage.getStorage();

        tutorial = storage.getTutorial();
        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        highscore = findViewById(R.id.highscore);

        question.setTypeface(question.getTypeface(), Typeface.BOLD);

        initTutorial();

        if(tutorial.isTutorial()) {
            setupTutorial(tutorial.getState());
            setup(0);
        } else {
            setupTutorial(Tutorial.NO_TUTORIAL);
            setup(storage.getQuestionNumber());
        }

        answerA.setOnClickListener(view -> MainActivity.this.onClick(answerA, Answer.ANSWERA));
        answerB.setOnClickListener(view -> MainActivity.this.onClick(answerB, Answer.ANSWERB));
        answerC.setOnClickListener(view -> MainActivity.this.onClick(answerC, Answer.ANSWERC));
        answerD.setOnClickListener(view -> MainActivity.this.onClick(answerD, Answer.ANSWERD));
        findViewById(R.id.activity_main).setOnClickListener(v -> setupTutorial(tutorial.click()));
    }

    private void onClick(Button button, Answer answer) {
        if(tutorial.isTutorial()) {
            setupTutorial(tutorial.click());
            return;
        }
        if(done) setup(storage.getQuestionNumber());
        else if(correct == answer) {
            button.setBackgroundColor(Color.GREEN);
            done = true;
            storage.resetQuestionNumber();
            storage.increaseScore();
            refreshScore();
        } else {
            button.setBackgroundColor(Color.RED);
            switch(correct) {
                case ANSWERA:
                    answerA.setBackgroundColor(Color.YELLOW);
                    break;
                case ANSWERB:
                    answerB.setBackgroundColor(Color.YELLOW);
                    break;
                case ANSWERC:
                    answerC.setBackgroundColor(Color.YELLOW);
                    break;
                case ANSWERD:
                    answerD.setBackgroundColor(Color.YELLOW);
                    break;
            }
            done = true;
            storage.resetQuestionNumber();
            storage.resetScore();
            refreshScore();
        }
    }

    private void setup(int questionNumber) {
        if(tutorial.isTutorial()) return;
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
        refreshScore();
    }

    private void initTutorial() {
        tutorialLower = findViewById(R.id.tutorial_lower);
        tutorialUpper = findViewById(R.id.tutorial_upper);
        arrow0 = findViewById(R.id.arrow_tutorial_0);
        arrow1A = findViewById(R.id.arrow_tutorial_1A);
        arrow1B = findViewById(R.id.arrow_tutorial_1B);
        arrow1C = findViewById(R.id.arrow_tutorial_1C);
        arrow1D = findViewById(R.id.arrow_tutorial_1D);
        arrow4 = findViewById(R.id.arrow_tutorial_4);
        arrow5 = findViewById(R.id.arrow_tutorial_5);
    }

    private void setupTutorial(int state) {
        hideTutorialComponents();
        switch(state) {
            case Tutorial.TUTORIAL_DONE:
                setup(storage.getQuestionNumber());
                tutorial.click();
            case Tutorial.NO_TUTORIAL:
                return;
            case 0:
                arrow0.setVisibility(View.VISIBLE);
                tutorialLower.setVisibility(View.VISIBLE);
                tutorialLower.setText(getResources().getIdentifier("tutorial_0", "string", getPackageName()));
                return;
            case 1:
                arrow1A.setVisibility(View.VISIBLE);
                arrow1B.setVisibility(View.VISIBLE);
                arrow1C.setVisibility(View.VISIBLE);
                arrow1D.setVisibility(View.VISIBLE);
                tutorialUpper.setVisibility(View.VISIBLE);
                tutorialUpper.setText(getResources().getIdentifier("tutorial_1", "string", getPackageName()));
                return;
            case 2:
                answerA.setBackgroundColor(Color.GREEN);
                tutorialUpper.setVisibility(View.VISIBLE);
                tutorialUpper.setText(getResources().getIdentifier("tutorial_2", "string", getPackageName()));
                return;
            case 3:
                answerA.setBackgroundColor(Color.YELLOW);
                answerB.setBackgroundColor(Color.RED);
                tutorialUpper.setVisibility(View.VISIBLE);
                tutorialUpper.setText(getResources().getIdentifier("tutorial_3", "string", getPackageName()));
                return;
            case 4:
                arrow4.setVisibility(View.VISIBLE);
                answerA.setBackgroundColor(Color.LTGRAY);
                answerB.setBackgroundColor(Color.LTGRAY);
                tutorialLower.setVisibility(View.VISIBLE);
                tutorialLower.setText(getResources().getIdentifier("tutorial_4", "string", getPackageName()));
                return;
            case 5:
                arrow5.setVisibility(View.VISIBLE);
                tutorialLower.setVisibility(View.VISIBLE);
                tutorialLower.setText(getResources().getIdentifier("tutorial_5", "string", getPackageName()));
                return;
            case 6:
                tutorialLower.setVisibility(View.VISIBLE);
                tutorialLower.setText(getResources().getIdentifier("tutorial_6", "string", getPackageName()));
        }
    }

    private void hideTutorialComponents() {
        tutorialLower.setVisibility(View.INVISIBLE);
        tutorialUpper.setVisibility(View.INVISIBLE);
        arrow0.setVisibility(View.INVISIBLE);
        arrow1A.setVisibility(View.INVISIBLE);
        arrow1B.setVisibility(View.INVISIBLE);
        arrow1C.setVisibility(View.INVISIBLE);
        arrow1D.setVisibility(View.INVISIBLE);
        arrow4.setVisibility(View.INVISIBLE);
        arrow5.setVisibility(View.INVISIBLE);
    }

    private void refreshScore() {
        score.setText("Score: " + storage.getScore());
        highscore.setText("Highscore: " + storage.getHighScore());
    }
}
