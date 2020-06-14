package xxl.mathematica.video

import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.Frame

import java.util.function.Consumer

class VideoFrameScanTest extends GroovyTestCase {
    CanvasFrame window = new CanvasFrame("CanvasFrame")

    void testVideoFrameScan() {
        VideoFrameScan.videoFrameScan(0, new Consumer<Frame>() {
            @Override
            void accept(Frame frame) {
                window.showImage(frame)
            }
        })
    }
}
