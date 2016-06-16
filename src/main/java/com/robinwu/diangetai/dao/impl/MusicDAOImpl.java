package com.robinwu.diangetai.dao.impl;

import com.robinwu.diangetai.dao.IMusicDAO;
import com.robinwu.diangetai.dao.mapper.MusicMapper;
import com.robinwu.diangetai.domain.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Robin on 16/6/14.
 */
@Repository
public class MusicDAOImpl implements IMusicDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateObject;


    public boolean addMusic(Music music) {
        String SQL = "insert into diangetai_music (id,uploader,name,path,played,star) values (?,?,?,?,?,?)";
        int result = jdbcTemplateObject.update(SQL,music.getId(),music.getUploader(),
                music.getName(),music.getPath(),music.isPlayed(),music.getStarNumber());
        return result == 1;
    }

    public boolean addMusic(String uploader,String name,String path) {
        String SQL = "insert into diangetai_music (uploader,name,path) values (?,?,?)";
        int result = jdbcTemplateObject.update(SQL,uploader,name,path);
        return result == 1;
    }

    public List<Music> prePlayList() {
        String SQL = "select * from diangetai_music where played = 0 ORDER by star DESC ";
        List <Music> musics = jdbcTemplateObject.query(SQL,
                new MusicMapper());
        return musics;
    }

    public Music randMusic() {
        String SQL = "select * from diangetai_music where played = 1;";
        List <Music> musics = jdbcTemplateObject.query(SQL,
                new MusicMapper());
        int rand = (int)(Math.random()*musics.size()-1);
        return musics.get(rand);
    }

    public Music playMusic() {
        List<Music> prePlayList = prePlayList();
        if(prePlayList.size() > 0) {
            return prePlayList.get(0);
        } else {
            return null;
        }
    }

    public boolean setPlayed(int id) {
        String SQL = "update diangetai_music set played = 1 where id = ?";
        int result = jdbcTemplateObject.update(SQL, id);
        return result == 1;
    }

    public boolean addStart(int id) {
        String SQL = "update diangetai_music set star = star+1 where id = ?";
        int result = jdbcTemplateObject.update(SQL, id);
        return result == 1;
    }
}
