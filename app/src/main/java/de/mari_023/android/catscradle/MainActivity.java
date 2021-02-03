package de.mari_023.android.catscradle;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    final int questions = 3;
    Button answerA, answerB, answerC, answerD;
    TextView question;
    Answer correct;
    boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);
        question = findViewById(R.id.question);

        question.setTextSize(22);
        answerA.setTextSize(20);
        answerB.setTextSize(20);
        answerC.setTextSize(20);
        answerD.setTextSize(20);

        setup(new Random().nextInt(questions));

        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(done) setup(new Random().nextInt(questions));
                else if(correct == Answer.ANSWERA) {
                    answerA.setBackgroundColor(Color.GREEN);
                    done = true;
                } else {
                    answerA.setBackgroundColor(Color.RED);
                }
            }
        });
        answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(done) setup(new Random().nextInt(questions));
                else if(correct == Answer.ANSWERB) {
                    answerB.setBackgroundColor(Color.GREEN);
                    done = true;
                } else {
                    answerB.setBackgroundColor(Color.RED);
                }
            }
        });
        answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(done) setup(new Random().nextInt(questions));
                else if(correct == Answer.ANSWERC) {
                    answerC.setBackgroundColor(Color.GREEN);
                    done = true;
                } else {
                    answerC.setBackgroundColor(Color.RED);
                }
            }
        });
        answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(done) setup(new Random().nextInt(questions));
                else if(correct == Answer.ANSWERD) {
                    answerD.setBackgroundColor(Color.GREEN);
                    done = true;
                } else {
                    answerD.setBackgroundColor(Color.RED);
                }
            }
        });
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
    }

    public enum Answer {
        ANSWERA(0), ANSWERB(1), ANSWERC(2), ANSWERD(3);

        private static final HashMap<Integer, Answer> map = new HashMap<>();

        static {
            for(Answer answer : Answer.values()) {
                map.put(answer.value, answer);
            }
        }

        private final int value;

        Answer(int value) {
            this.value = value;
        }

        public static Answer valueOf(int answer) {
            return map.get(answer);
        }
    }
}