package xxl.mathematica.video;

import io.vavr.control.Try;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;

import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * 视频转发
 */
public class VideoFrameRedirect {
    /**
     * 视频帧转发
     *
     * @param src
     * @param dst
     * @param f
     */
    public static void videoFrameRedirect(String src, String dst, Function<Frame, Frame> f) {
        Try.ofCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(src);//使用ffmpeg抓取器
                grabber.start();//开启抓取器

                int width = grabber.getImageWidth();
                int height = grabber.getImageHeight();

                FrameRecorder recorder = FrameRecorder.createDefault(dst, width, height);
                //设置视频编码和格式
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
                recorder.setFormat("flv");//封装格式，如果是推送到rtmp就必须是flv封装格式
                recorder.setFrameRate(grabber.getFrameRate());

                recorder.start();//开启记录器

                while (true) {
                    Frame frame = grabber.grabImage();
                    if (frame == null) {
                        break;
                    }
                    System.err.println(frame.timestamp);
                    recorder.setTimestamp(frame.timestamp);
                    //进行图片处理
                    if (f != null) {
                        frame = f.apply(frame.clone());
                    }
                    recorder.record(frame);
                }

                recorder.stop();
                recorder.release();
                grabber.stop();
                grabber.release();
                return null;
            }
        }).get();
    }
}
