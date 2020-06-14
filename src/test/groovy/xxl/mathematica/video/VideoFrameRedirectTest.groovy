package xxl.mathematica.video

import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.Java2DFrameUtils
import org.bytedeco.opencv.opencv_core.Mat

import java.util.function.Function

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor

class VideoFrameRedirectTest extends GroovyTestCase {
    void testVideoRedirect() {
        VideoFrameRedirect.videoRedirect("D:\\pet.mp4", "rtmp://localhost:1935/live/abc", new Function<Frame, Frame>() {
            @Override
            Frame apply(Frame frame) {
                Mat dst = new Mat()
                cvtColor(Java2DFrameUtils.toMat(frame), dst, COLOR_BGR2GRAY)
                return Java2DFrameUtils.toFrame(dst)
            }
        })
    }
}
