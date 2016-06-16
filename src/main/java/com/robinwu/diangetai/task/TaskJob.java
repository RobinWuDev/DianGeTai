package com.robinwu.diangetai.task;

import com.robinwu.diangetai.config.Config;
import com.robinwu.diangetai.dao.IMusicDAO;
import com.robinwu.diangetai.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 16/6/15.
 */
@Service
public class TaskJob {
    private static final Logger logger = LoggerFactory.getLogger("JOB");
    @Autowired
    private IMusicDAO dao;

    private int index = 0;
    private int offset = Config.UPDATE_NUMBER;

    private String m3u8Path;
    List<Music.MusicTS> playList;

    public TaskJob() {
        m3u8Path = Config.getOutputDir() + "/index.m3u8";
        File file = new File(m3u8Path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playList = new ArrayList<Music.MusicTS>();
    }

    public void job1() {
        logger.info("test");
        try {
            offset = Config.UPDATE_NUMBER;
            File file = new File(m3u8Path);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            if(playList.size() <= 3){
                List<Music.MusicTS> list = getPrePlayList();
                if(list != null) {
                    playList.addAll(list);
                }
            }

            if(playList.size() > 0) {
                if(playList.size() < offset) {
                    offset = playList.size();
                }
                List<Music.MusicTS> prePlay = playList.subList(0,offset);
                updateM3U8(bufferedWriter,prePlay,index);
                playList.removeAll(prePlay);
                index += offset;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long updateM3U8(BufferedWriter bufferedWriter, List<Music.MusicTS> playList, int index) throws Exception{
        bufferedWriter.write("#EXTM3U\n" +
                "#EXT-X-VERSION:3\n" +
                "#EXT-X-MEDIA-SEQUENCE:" + index + "\n" +
                "#EXT-X-TARGETDURATION:8\n");
        long canPlayTime = 0;
        for (Music.MusicTS musicTS: playList) {
            bufferedWriter.append("#EXTINF:" + musicTS.getDuration() + ",\n");
            bufferedWriter.append(Config.DOMAIN + "/file/" + musicTS.getPath() +"\n");
            canPlayTime += Double.valueOf(musicTS.getDuration());
            System.out.println(Config.DOMAIN + "/file/" + musicTS.getPath());
        }
        bufferedWriter.flush();
        return canPlayTime;
    }

    public List<Music.MusicTS> getPrePlayList() throws Exception{
        Music playMusic = dao.playMusic();
        if(playMusic == null){
            playMusic = dao.randMusic();
            if(playMusic == null) {
                return null;
            }
        }
        playMusic.load();
        dao.setPlayed(playMusic.getId());
        List<Music.MusicTS> musicTSes = playMusic.getTsList();
        if(musicTSes.size() == 0) {
            return getPrePlayList();
        }
        return musicTSes;
    }
}
