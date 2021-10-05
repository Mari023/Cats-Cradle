package de.mari_023.android.catscradle;

import java.util.Random;

public class Storage {
    private static Storage INSTANCE;
    private int score;
    private int highscore;
    private int questionNumber;

    private Storage() {
        score = MainActivity.sharedpreferences.getInt("score", 0);
        highscore = MainActivity.sharedpreferences.getInt("highscore", 0);
        questionNumber = MainActivity.sharedpreferences.getInt("questionNumber", -1);
    }

    public static Storage getStorage() {
        if(INSTANCE == null) return INSTANCE = new Storage();
        return INSTANCE;
    }

    private static int getNewQuestionID() {
        return new Random().nextInt(MainActivity.questions);
    }

    public int getScore() {
        return score;
    }

    private int setScore(int score) {
        MainActivity.sharedpreferences.edit().putInt("score", score).apply();
        return this.score = score;
    }

    public void resetScore() {
        setScore(0);
    }

    public void increaseScore() {
        int s = setScore(getScore() + 1);
        if(s > getHighScore()) setHighScore(s);
    }

    public int getHighScore() {
        return highscore;
    }

    private void setHighScore(int highscore) {
        this.highscore = highscore;
        MainActivity.sharedpreferences.edit().putInt("highscore", highscore).apply();
    }

    public int getQuestionNumber() {
        return questionNumber == -1 ? setQuestionNumber(getNewQuestionID()) : questionNumber;
    }

    private int setQuestionNumber(int questionNumber) {
        MainActivity.sharedpreferences.edit().putInt("questionNumber", questionNumber).apply();
        return this.questionNumber = questionNumber;
    }

    public void resetQuestionNumber() {
        setQuestionNumber(-1);
    }
}
