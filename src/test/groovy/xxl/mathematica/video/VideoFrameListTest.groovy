package xxl.mathematica.video

import io.vavr.Tuple2
import org.bytedeco.javacv.Frame

import javax.imageio.ImageIO
import java.util.function.Consumer

class VideoFrameListTest extends GroovyTestCase {
    void testVideoFrameList() {
        long start = System.currentTimeMillis();
        io.vavr.collection.List.ofAll(VideoFrameList.videoFrameList("D:\\big.mp4", 10, false))
                .zipWithIndex()
                .forEach(new Consumer<Tuple2<Frame, Integer>>() {
                    @Override
                    public void accept(Tuple2<Frame, Integer> tuple2) {
                        try {
                            ImageIO.write(EnhanceFrameUtils.toBufferedImage(tuple2._1), "jpg", new File("D:\\big" + tuple2._2 + ".jpg"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        System.out.println("time: " + (System.currentTimeMillis() - start));
    }
}
