package xxl.mathematica.video;

import io.vavr.control.Try;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
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
     * @return
     */
    public static <T> List<T> videoFrameList(String videoFile, int n, Class<T> cls) {
        return Try.ofCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(new File(videoFile));
                grabber.start();
                int frameCount = grabber.getLengthInVideoFrames();
                List<T> res = new ArrayList<>();
                int step = frameCount / n;
                int offset = step / 2;
                int i = 0;
                while (i < frameCount) {
                    Frame f = grabber.grabImage();
                    if ((i >= offset) && (i - offset) % step == 0 && f != null) {
                        T t;
                        if (cls == BufferedImage.class) {
                            t = (T) Java2DFrameUtils.toBufferedImage(f);
                        } else if (cls == IplImage.class) {
                            t = (T) Java2DFrameUtils.toIplImage(f);
                        } else if (cls == Mat.class) {
                            t = (T) Java2DFrameUtils.toMat(f);
                        } else if (cls == org.opencv.core.Mat.class) {
                            t = (T) EnhanceFrameUtils.toCoreMat(f);
                        } else {
                            throw new IllegalArgumentException("不支持转换到" + cls.getSimpleName());
                        }
                        if (res.size() < n) {
                            res.add(t);
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
