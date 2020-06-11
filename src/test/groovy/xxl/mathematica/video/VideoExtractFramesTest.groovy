package xxl.mathematica.video

import org.bytedeco.javacv.Frame
import xxl.mathematica.Rule
import xxl.mathematica.image.ShowImage

import javax.imageio.ImageIO
import java.util.function.Function

class VideoExtractFramesTest extends GroovyTestCase {
    void testVideoExtractFrames() {
        def files = io.vavr.collection.List.ofAll(VideoExtractFrames.videoExtractFrames("D:\\big.mp4", [333, 666, 999]))
                .map(new Function<Rule<Integer, Frame>, String>() {
                    @Override
                    String apply(Rule<Integer, Frame> rule) {
                        String file = "D:\\pet" + rule.key + ".jpg"
                        ImageIO.write(EnhanceFrameUtils.toBufferedImage(rule.value), "jpg", new File(file))
                        return file
                    }
                }).asJava()
        ShowImage.showImage(files as String[])
    }
}
