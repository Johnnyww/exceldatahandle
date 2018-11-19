package com.chenjunxin.exceldatahandle;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by chenjunxin on 2018/11/7
 */
public class ThumbnailsTests {

    public static void main(String[] args) {
        String pic1 = "E:\\timg.jpg";
        File pic2 = new File("E:\\1.jpg");
        String toPic = "E:\\w.jpg";
        // outputQuality就是用来控制图片质量的(控制图片质量，图片尺寸不变)
//        try {
//            Thumbnails.of(pic1).scale(1f).outputQuality(0.25f).toFile(toPic);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        // 加水印
        try {
            Thumbnails.of(pic1).scale(0.8)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(pic2), 0.5f)
                    .outputQuality(0.8f).toFile(toPic);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}