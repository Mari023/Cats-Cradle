package de.mari_023.android.catscradle;

public class Tutorial {
    public static final int NO_TUTORIAL = -1;
    public static final int TUTORIAL_DONE = -2;
    public static final int LAST_STATE = 6;
    private final Storage storage;
    private int state;

    public Tutorial(Storage s) {
        storage = s;
        state = s.getTutorialState();
    }

    public boolean isTutorial() {
        return state > NO_TUTORIAL;
    }

    public int click() {
        switch(state) {
            case NO_TUTORIAL:
                return NO_TUTORIAL;
            case LAST_STATE:
                storage.setTutorialState(NO_TUTORIAL);
                return state = TUTORIAL_DONE;
            case TUTORIAL_DONE:
                return storage.setTutorialState(state = NO_TUTORIAL);
            default:
                state++;
                return state;
        }
    }

    public int getState() {
        return state;
    }
}
