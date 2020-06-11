package xxl.mathematica.video;

import io.vavr.control.Try;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import xxl.mathematica.predication.MemberQ;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 提取视频的指定帧
 */
public class VideoExtractFrames {
    /**
     * 提取指定帧
     *
     * @param videoFile
     * @param frames    离散的帧，如果不在范围内，直接忽略
     * @return
     */
    public static List<Frame> videoExtractFrames(String videoFile, List<Integer> frames) {
        return Try.ofCallable(new Callable<List<Frame>>() {
            @Override
            public List<Frame> call() throws Exception {
                List<Integer> indexes = new ArrayList<>(frames);
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(new File(videoFile));
                grabber.start();
                int frameCount = grabber.getLengthInVideoFrames();
                List<Frame> res = new ArrayList<>();
                int i = 0;
                while (i < frameCount) {
                    Frame frame = grabber.grabImage();
                    if (MemberQ.memberQ(indexes, i) && frame != null) {
                        res.add(frame.clone());
                        //移除索引
                        indexes.remove((Integer) i);
                        if (indexes.size() == 0) {
                            break;
                        }
                    }
                    ++i;
                }
                grabber.stop();
                return res;
            }
        }).get();
    }
}
