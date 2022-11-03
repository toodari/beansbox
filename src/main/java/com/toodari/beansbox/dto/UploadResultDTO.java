package com.toodari.beansbox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO {

    private String fileName;

    private String uuid;

    private String folderPath;

    public String getImageURL(){
        try {
            return URLEncoder.encode(folderPath+"/" +uuid + fileName,"UTF-8");

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    // 추가
    public String getThumbnailURL(){
        try {
            return URLEncoder.encode(folderPath + "/s_" +uuid + "_" +fileName,"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return "";
    }

}
