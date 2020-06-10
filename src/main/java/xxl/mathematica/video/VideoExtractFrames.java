package xxl.mathematica.video;

import io.vavr.control.Try;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;
import xxl.mathematica.predication.MemberQ;

import java.awt.image.BufferedImage;
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
    public static <T> List<T> videoExtractFrames(String videoFile, List<Integer> frames, Class<T> cls) {
        return Try.ofCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                List<Integer> indexes = new ArrayList<>(frames);
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(new File(videoFile));
                grabber.start();
                int frameCount = grabber.getLengthInVideoFrames();
                List<T> res = new ArrayList<>();
                int i = 0;
                while (i < frameCount) {
                    Frame frame = grabber.grabImage();
                    if (MemberQ.memberQ(indexes, i) && frame != null) {
                        T t;
                        if (cls == BufferedImage.class) {
                            t = (T) Java2DFrameUtils.toBufferedImage(frame);
                        } else if (cls == IplImage.class) {
                            t = (T) Java2DFrameUtils.toIplImage(frame);
                        } else if (cls == Mat.class) {
                            t = (T) Java2DFrameUtils.toMat(frame);
                        } else if (cls == org.opencv.core.Mat.class) {
                            t = (T) EnhanceFrameUtils.toCoreMat(frame);
                        } else {
                            throw new IllegalArgumentException("不支持转换到" + cls.getSimpleName());
                        }
                        res.add(t);
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
