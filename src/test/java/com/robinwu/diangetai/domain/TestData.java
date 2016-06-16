package com.robinwu.diangetai.domain;

/**
 * Created by Robin on 16/6/14.
 */
public class TestData {
    public static Music getTempMusic(int id,boolean played,int star) {
        Music music = new Music();
        music.setId(id);
        music.setUploader("RobinWu");
        music.setName("Music1");
        music.setPath("/usr/lib");
        music.setPlayed(played);
        music.setStarNumber(star);
        return music;
    }

    public static Music getTempMusic() {
        Music music = new Music();
        music.setId(11);
        music.setUploader("RobinWu");
        music.setName("Music1");
        music.setPath("/usr/lib");
        music.setPlayed(false);
        music.setStarNumber(33);
        return music;
    }
}
