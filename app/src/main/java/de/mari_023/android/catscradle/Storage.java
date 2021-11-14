package de.mari_023.android.catscradle;

import java.util.Random;

public class Storage {
    private static Storage INSTANCE;
    private int score;
    private int highscore;
    private int questionNumber;
    private final int[] lastQuestions = new int[50];
    private int lastQuestionPosition;

    private Storage() {
        score = MainActivity.sharedpreferences.getInt("score", 0);
        highscore = MainActivity.sharedpreferences.getInt("highscore", 0);
        questionNumber = MainActivity.sharedpreferences.getInt("questionNumber", -1);
        String[] lastQ = MainActivity.sharedpreferences.getString("lastQuestions", "").split(",");
        for(int i = 0; i < lastQ.length && i < lastQuestions.length; i++) {
            try {
                lastQuestions[i] = Integer.parseInt(lastQ[i]);
            } catch(Exception ignored) {}
        }
        lastQuestionPosition = MainActivity.sharedpreferences.getInt("lastQuestionPosition", 0);
    }

    public static Storage getStorage() {
        if(INSTANCE == null) return INSTANCE = new Storage();
        return INSTANCE;
    }

    private int getNewQuestionID() {
        int id;
        do {
            id = new Random().nextInt(MainActivity.questions);
        } while(arrayContains(lastQuestions, id));
        return id;
    }

    private boolean arrayContains(int[] array, int contain) {
        for(int i : array) if(i == contain) return true;
        return false;
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
        if(questionNumber != -1) setLastQuestion(questionNumber);
        return this.questionNumber = questionNumber;
    }

    public void resetQuestionNumber() {
        setQuestionNumber(-1);
    }

    public void setLastQuestion(int i) {
        lastQuestions[lastQuestionPosition] = i;
        lastQuestionPosition++;
        StringBuilder lastQ = new StringBuilder();
        for(int lastQuestion : lastQuestions) {
            lastQ.append(lastQuestion).append(",");
        }
        lastQ.deleteCharAt(lastQ.length() - 1);
        MainActivity.sharedpreferences.edit().putString("questionNumber", lastQ.toString()).apply();
        MainActivity.sharedpreferences.edit().putInt("questionNumber", lastQuestionPosition).apply();
    }
}
