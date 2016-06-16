package com.robinwu.diangetai.task;

import com.robinwu.diangetai.config.Config;
import com.robinwu.diangetai.dao.IMusicDAO;
import com.robinwu.diangetai.domain.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * Created by Robin on 16/6/15.
 */
@Service
public class ClipMusicJob {

    @Autowired
    private IMusicDAO dao;

    public ClipMusicJob() {
    }

    public void job1() {
        System.out.println("clip");
        Music currentPlayMusic = dao.playMusic();
        if(currentPlayMusic == null){
            return;
        }
        try {
            currentPlayMusic.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
