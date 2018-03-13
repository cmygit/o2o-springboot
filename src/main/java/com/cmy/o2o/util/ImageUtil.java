package com.cmy.o2o.util;

import com.cmy.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author : cmy
 * Date   : 2018-03-01 11:25.
 * desc   :
 */
public class ImageUtil {

    private static String resourcesBasePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Random r = new Random();

    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png，jpg等
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则差un关键
        makeDirPath(targetAddr);
        // 获取文件存储的相对路径（带文件名）
        String relativeAddr = targetAddr + realFileName + extension;
        // 获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        // 调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(300, 300)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(resourcesBasePath + "/tempImg/logo_small.jpg")), 0.25f)
                    .outputQuality(0.8f)
                    .toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回图片的相对路径
        return relativeAddr;
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png，jpg等
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则差un关键
        makeDirPath(targetAddr);
        // 获取文件存储的相对路径（带文件名）
        String relativeAddr = targetAddr + realFileName + extension;
        // 获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        // 调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 647)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(resourcesBasePath + "/tempImg/logo_small.jpg")), 0.25f)
                    .outputQuality(0.9f)
                    .toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回图片的相对路径
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及的目录，如/home/work/img/xxx.jpg，那么home work img这些文件夹需要自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟 + 五位随机数
     * @return
     */
    public static String getRandomFileName() {
        // 获取5位随机数
        int rannum = r.nextInt(89999) + 10000;
        String nowTImeStr = sDateFormat.format(new Date());
        return nowTImeStr + rannum;
    }

    /**
     * 判断storePath是文件的路径还是目录的路径
     * 如果storePath是文件路径则删除该文件
     * 如果storePath是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPaht = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPaht.exists()) {
            // 如果storePath是目录路径则删除该目录下的所有文件，但不会删除该目录本身
            if (fileOrPaht.isDirectory()) {
                File[] files = fileOrPaht.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            // 删除该文件或删除该目录
            fileOrPaht.delete();
        }
    }

    public static void main(String[] args) {
        System.out.println(resourcesBasePath);
    }
}
