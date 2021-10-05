package de.mari_023.android.catscradle;

import java.util.HashMap;

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
