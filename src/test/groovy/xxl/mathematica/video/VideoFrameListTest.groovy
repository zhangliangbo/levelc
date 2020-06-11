package xxl.mathematica.video


import org.bytedeco.javacv.Frame
import xxl.mathematica.Rule

import javax.imageio.ImageIO
import java.util.function.Consumer

class VideoFrameListTest extends GroovyTestCase {
    void testVideoFrameList() {
        io.vavr.collection.List.ofAll(VideoFrameList.videoFrameList("D:\\big.mp4", 10, false))
                .forEach(new Consumer<Rule<Integer, Frame>>() {
                    @Override
                    void accept(Rule<Integer, Frame> rule) {
                        try {
                            ImageIO.write(EnhanceFrameUtils.toBufferedImage(rule.value), "jpg", new File("D:\\big" + rule.getKey() + ".jpg"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
    }
}
