package xxl.mathematica.video;

import io.vavr.control.Try;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import xxl.mathematica.list.Range;
import xxl.mathematica.list.Subdivide;
import xxl.mathematica.random.RandomSample;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 从视频中提取n张图片
 */
public class VideoFrameList {
    /**
     * 均匀地提取n张图片
     *
     * @param videoFile
     * @param n
     * @param uniform   是否均匀
     * @return
     */
    public static List<Frame> videoFrameList(String videoFile, int n, boolean uniform) {
        return Try.ofCallable(new Callable<List<Frame>>() {
            @Override
            public List<Frame> call() throws Exception {
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(new File(videoFile));
                grabber.start();
                int len = grabber.getLengthInVideoFrames();
                grabber.stop();
                List<Integer> indexes;
                if (uniform) {
                    indexes = Subdivide.subdivide(0, len - 1, n);
                } else {
                    indexes = RandomSample.randomSample(Range.range(len), n);
                }
                return VideoExtractFrames.videoExtractFrames(videoFile, indexes);
            }
        }).get();
    }
}
