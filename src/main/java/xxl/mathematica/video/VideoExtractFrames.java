package xxl.mathematica.video;

import io.vavr.control.Try;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import xxl.mathematica.Rule;
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
     * 提取指定帧[帧序号，帧数据]
     *
     * @param videoFile
     * @param frames    离散的帧，如果不在范围内，直接忽略
     * @return
     */
    public static List<Rule<Integer, Frame>> videoExtractFrames(String videoFile, List<Integer> frames) {
        return Try.ofCallable(new Callable<List<Rule<Integer, Frame>>>() {
            @Override
            public List<Rule<Integer, Frame>> call() throws Exception {
                List<Integer> indexes = new ArrayList<>(frames);
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(new File(videoFile));
                grabber.start();
                int frameCount = grabber.getLengthInVideoFrames();
                List<Rule<Integer, Frame>> res = new ArrayList<>();
                int i = 0;
                while (i < frameCount) {
                    Frame frame = grabber.grabImage();
                    if (MemberQ.memberQ(indexes, i) && frame != null) {
                        res.add(Rule.valueOf(i, frame.clone()));
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
