package com.robinwu.diangetai.domain;

import com.robinwu.diangetai.config.Config;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 16/6/14.
 */
public class Music {
    private Integer id;
    private String uploader;
    private String name;
    private String path;
    private boolean isPlayed;
    private Integer starNumber;
    private List<MusicTS> tsList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public Integer getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(Integer starNumber) {
        this.starNumber = starNumber;
    }

    public List<MusicTS> getTsList() {
        return tsList;
    }

    @Override
    public boolean equals(Object obj) {
        String[] key = {"id","uploader","name","path","isPlayed","starNumber"};
        return EqualsBuilder.reflectionEquals(this,obj,key);
    }

    @Override
    public int hashCode() {
        String[] key = {"id","uploader","name","path","isPlayed","starNumber"};
        return HashCodeBuilder.reflectionHashCode(this,key);
    }

    public void load() throws Exception{
        tsList = new ArrayList<MusicTS>();

        if(tsList.isEmpty()) {
            tsList = loadTsList();
            if(tsList.isEmpty()) {
                mp3ToM3U8();
                tsList = loadTsList();
            }
        }
    }

    private void mp3ToM3U8() throws Exception{
        File file = new File(this.getPath());

        String inputFile = this.getPath();
        String outputDir = Config.getOutputDir() + "/" + this.getId();
        String m3u8File = outputDir + "/index.m3u8";
        String outputFile = outputDir + "/%03d.aac";

        File dir = new File(outputDir);
        if(!dir.exists()) {
            dir.mkdir();
        }

        String cmd = String.format("/usr/local/bin/ffmpeg -i '%s' -f segment " +
                "-segment_time 7 -segment_list %s '%s'",inputFile,m3u8File,outputFile);

        String[] cmdA = { "/bin/sh", "-c", cmd };
        Runtime run = Runtime.getRuntime();

        Process process = run.exec(cmdA);

        LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
        LineNumberReader errorString = new LineNumberReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = br.readLine()) != null || (line = errorString.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
        errorString.close();
    }

    private List<MusicTS> loadTsList() throws Exception{
        List<MusicTS> tempList = new ArrayList<MusicTS>();

        String name = this.getName();
        String path = Config.getOutputDir() + "/" + this.getId() + "/index.m3u8";
        File file = new File(path);

        if(file.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            String key = "";
            String value = "";

            while((line = bufferedReader.readLine()) != null) {
                if(line.contains("#EXTINF:")) {
                    value = line;
                    key = this.getId() + "/" + bufferedReader.readLine();
                    MusicTS ts = new MusicTS();
                    ts.setPath(key);
                    ts.setDuration(value.replace("#EXTINF:","").replace(",",""));
                    tempList.add(ts);
                }
                key = "";
                value = "";
            }
        }
        return tempList;
    }

    public class MusicTS {
        private String path;
        private String duration;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
