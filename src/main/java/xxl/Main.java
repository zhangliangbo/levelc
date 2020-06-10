package xxl;

import io.vavr.Tuple2;
import xxl.mathematica.video.VideoFrameList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        io.vavr.collection.List.ofAll(VideoFrameList.videoFrameList("D:\\pet.mp4", 10, BufferedImage.class))
                .zipWithIndex()
                .forEach(new Consumer<Tuple2<BufferedImage, Integer>>() {
                    @Override
                    public void accept(Tuple2<BufferedImage, Integer> t) {
                        try {
                            ImageIO.write(t._1, "jpg", new File("D:\\pet" + t._2 + ".jpg"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        System.out.println("time: " + (System.currentTimeMillis() - start));
    }
}
