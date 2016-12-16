package com.chenkovegor.gameofthrones.data.managers;

public enum Houses {

    STARKS(362),
    LANNISTERS(229),
    TARGARYENS(378);

    private int mId;

    Houses(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

}
