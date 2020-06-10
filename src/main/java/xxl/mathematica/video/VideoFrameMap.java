package xxl.mathematica.video;

import io.vavr.control.Try;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

/**
 * 作用于每个数据帧
 */
public class VideoFrameMap {
    /**
     * 作用函数于每个帧[index, frame]
     * index从0开始
     *
     * @param videoFile
     * @param f
     * @param <T>
     * @return
     */
    public static <T> List<T> videoFrameMap(String videoFile, BiFunction<Integer, Frame, T> f) {
        return Try.ofCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(new File(videoFile));
                grabber.start();
                int frameCount = grabber.getLengthInVideoFrames();
                List<T> res = new ArrayList<>();
                int i = 0;
                while (i < frameCount) {
                    Frame frame = grabber.grabImage();
                    if (frame != null) {
                        res.add(f.apply(i, frame));
                    }
                    ++i;
                }
                grabber.stop();
                return res;
            }
        }).get();
    }
}
