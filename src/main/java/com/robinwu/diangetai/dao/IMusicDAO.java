package com.robinwu.diangetai.dao;

import com.robinwu.diangetai.domain.Music;

import java.util.List;

/**
 * Created by Robin on 16/6/14.
 */
public interface IMusicDAO {

    public boolean addMusic(Music music);

    public boolean addMusic(String uploader,String name,String path);

    public List<Music> prePlayList();

    public Music randMusic();

    public Music playMusic();

    public boolean setPlayed(int id);

    public boolean addStart(int id);
}
