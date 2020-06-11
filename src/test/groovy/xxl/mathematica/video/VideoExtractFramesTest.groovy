package xxl.mathematica.video

import io.vavr.Tuple2
import org.bytedeco.javacv.Frame
import xxl.mathematica.image.ShowImage
import xxl.mathematica.list.Range

import javax.imageio.ImageIO
import java.util.function.Function

class VideoExtractFramesTest extends GroovyTestCase {
    void testVideoExtractFrames() {
        def files = io.vavr.collection.List.ofAll(VideoExtractFrames.videoExtractFrames("D:\\big.mp4", Range.range(999, 1009)))
                .zipWithIndex()
                .map(new Function<Tuple2<Frame, Integer>, String>() {
                    @Override
                    String apply(Tuple2<Frame, Integer> tuple2) {
                        String file = "D:\\pet" + tuple2._2 + ".jpg"
                        ImageIO.write(EnhanceFrameUtils.toBufferedImage(tuple2._1), "jpg", new File(file))
                        return file
                    }
                }).asJava()
        ShowImage.showImage(files as String[])
    }
}
