package xxl.mathematica.video

import org.bytedeco.javacv.Frame

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.util.function.BiFunction

class VideoFrameMapTest extends GroovyTestCase {
    void testVideoFrameMap() {
        List<String> s = VideoFrameMap.videoFrameMap("D:\\big.mp4", new BiFunction<Integer, Frame, String>() {
            @Override
            String apply(Integer index, Frame frame) {
                BufferedImage bi = EnhanceFrameUtils.toBufferedImage(frame)
                ImageIO.write(bi, "jpg", new File("D:\\abc\\pet" + index + ".jpg"));
            }
        })
        println(s)
    }
}
