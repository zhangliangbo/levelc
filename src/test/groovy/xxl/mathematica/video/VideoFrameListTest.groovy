package xxl.mathematica.video

import org.bytedeco.javacv.Frame
import xxl.mathematica.Rule
import xxl.mathematica.image.ShowImage

import javax.imageio.ImageIO
import java.util.function.Function

class VideoFrameListTest extends GroovyTestCase {
    void testVideoFrameList() {
        def files = io.vavr.collection.List.ofAll(VideoFrameList.videoFrameList("D:\\big.mp4", 3, false))
                .map(new Function<Rule<Integer, Frame>, String>() {
                    @Override
                    String apply(Rule<Integer, Frame> rule) {
                        String file = "D:\\big" + rule.getKey() + ".jpg"
                        ImageIO.write(EnhanceFrameUtils.toBufferedImage(rule.value), "jpg", new File(file))
                        return file
                    }
                }).asJava()
        ShowImage.showImage(files as String[])
    }
}
