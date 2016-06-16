package com.robinwu.diangetai.controller;

import com.robinwu.diangetai.config.Config;
import com.robinwu.diangetai.dao.IMusicDAO;
import com.robinwu.diangetai.domain.Music;
import com.robinwu.diangetai.domain.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Robin on 16/6/14.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private IMusicDAO dao;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/star",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public WebResponse star(@RequestParam("id") int id) {
        boolean result =  dao.addStart(id);
        WebResponse response;
        if(result) {
            response = WebResponse.getOKResponse("star OK");
        } else {
            response = WebResponse.getErrorResponse("star Fail");
        }
        return response;
    }

    @RequestMapping(value = "/playList",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public WebResponse prePlayList() {
        List<Music> list = dao.prePlayList();
        WebResponse response = WebResponse.getOKResponse(list);
        return response;
    }

    @RequestMapping(value = "upload",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public WebResponse uploadFile(@RequestParam("file") MultipartFile file) {
        WebResponse response = WebResponse.getErrorResponse("upload Faild");
        if (!file.isEmpty()) {
            try {
                String name = file.getOriginalFilename().replace(".mp3","");
                String path = Config.getUploadPath() + "/" + file.getOriginalFilename();
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(path)));
                int result = FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                if(result > 0){
                    response = WebResponse.getOKResponse(path);
                } else {
                    response = WebResponse.getErrorResponse("uploadFail");
                }
            }
            catch (Exception e) {
                response = WebResponse.getErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public WebResponse add(@RequestParam("uploader") String uploader,
                           @RequestParam("path") String path,
                           @RequestParam("name") String name) {
        WebResponse response;
        if(uploader.isEmpty() || path.isEmpty() || name.isEmpty()) {
            response = WebResponse.getErrorResponse("name is null or uploader is null");
            return response;
        }

        boolean result = dao.addMusic(uploader,name,path);
        if(result){
            response = WebResponse.getOKResponse("OK");
        } else {
            response = WebResponse.getErrorResponse("Faild");
        }
        return response;
    }
}
