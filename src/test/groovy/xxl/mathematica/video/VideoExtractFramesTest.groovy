package xxl.mathematica.video

import io.vavr.Tuple2
import org.bytedeco.javacv.Frame

import javax.imageio.ImageIO
import java.util.function.Consumer

class VideoExtractFramesTest extends GroovyTestCase {
    void testVideoExtractFrames() {
        io.vavr.collection.List.ofAll(VideoExtractFrames.videoExtractFrames("D:\\big.mp4", [0, 1, 2]))
                .zipWithIndex()
                .forEach(new Consumer<Tuple2<Frame, Integer>>() {
                    @Override
                    void accept(Tuple2<Frame, Integer> tuple2) {
                        try {
                            ImageIO.write(EnhanceFrameUtils.toBufferedImage(tuple2._1), "jpg", new File("D:\\pet" + tuple2._2 + ".jpg"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })

    }
}
