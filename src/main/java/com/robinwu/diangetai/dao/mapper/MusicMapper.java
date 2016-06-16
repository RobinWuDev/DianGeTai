package com.robinwu.diangetai.dao.mapper;

import com.robinwu.diangetai.domain.Music;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Robin on 16/6/14.
 */
public class MusicMapper implements RowMapper<Music> {

    public Music mapRow(ResultSet resultSet, int i) throws SQLException {
        Music music = new Music();
        music.setId(resultSet.getInt("id"));
        music.setUploader(resultSet.getString("uploader"));
        music.setName(resultSet.getString("name"));
        music.setPath(resultSet.getString("path"));
        music.setPlayed(resultSet.getBoolean("played"));
        music.setStarNumber(resultSet.getInt("star"));
        return music;
    }
}
