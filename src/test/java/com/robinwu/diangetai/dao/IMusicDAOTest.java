package com.robinwu.diangetai.dao;

import com.robinwu.diangetai.domain.Music;
import com.robinwu.diangetai.domain.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Robin on 16/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/WEB-servlet.xml"})
@ActiveProfiles("product")
public class IMusicDAOTest {
    @Autowired
    private IMusicDAO dao;

    @Test
    @Transactional
    public void testCreate() {
        boolean result = dao.addMusic("RobinWu","Music1","/usr/lib");
        assertEquals(result,true);
    }

    @Test
    @Transactional
    public void testCreateWithMusic() {
        Music music = TestData.getTempMusic();
        boolean result = dao.addMusic(music);
        assertEquals(result,true);
    }

    @Test
    @Transactional
    public void testPrePlayList() {
        Music music1 = TestData.getTempMusic(1,true,30);
        Music music2 = TestData.getTempMusic(2,false,29);
        Music music3 = TestData.getTempMusic(3,false,50);

        dao.addMusic(music1);
        dao.addMusic(music2);
        dao.addMusic(music3);

        List<Music> prePlayList = dao.prePlayList();
        assertEquals(prePlayList.size(),2);
        assertEquals(prePlayList.get(0),music3);
    }

    @Test
    @Transactional
    public void testPlayMusic() {
        Music music1 = TestData.getTempMusic(1,true,30);
        Music music2 = TestData.getTempMusic(2,false,29);
        Music music3 = TestData.getTempMusic(3,false,50);

        dao.addMusic(music1);
        dao.addMusic(music2);
        dao.addMusic(music3);

        Music music = dao.playMusic();
        assertEquals(music,music3);
    }

    @Test
    @Transactional
    public void testRandMusic() {
        Music music1 = TestData.getTempMusic(1,true,30);

        dao.addMusic(music1);

        Music music = dao.randMusic();
        assertEquals(music,music1);
    }

    @Test
    @Transactional
    public void testSetPlayed() {
        Music music1 = TestData.getTempMusic(1,false,30);

        dao.addMusic(music1);
        dao.setPlayed(music1.getId());

        Music playMusic = dao.playMusic();
        assertNull(playMusic);
    }

    @Test
    @Transactional
    public void testAddStart() {
        Music music1 = TestData.getTempMusic(1,false,30);

        dao.addMusic(music1);
        dao.addStart(music1.getId());

        Music playMusic = dao.playMusic();
        assertEquals((int)playMusic.getStarNumber(),(int)(music1.getStarNumber()+1));
    }
}
