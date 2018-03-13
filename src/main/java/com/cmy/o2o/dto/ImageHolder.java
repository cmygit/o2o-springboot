package com.cmy.o2o.dto;

import lombok.Data;

import java.io.InputStream;

/**
 * Author : cmy
 * Date   : 2018-03-06 11:07.
 * desc   : 封装商品图片信息
 */
@Data
public class ImageHolder {

    private InputStream image;
    private String imageName;

    public ImageHolder(InputStream image, String imageName) {
        this.imageName = imageName;
        this.image = image;
    }
}
