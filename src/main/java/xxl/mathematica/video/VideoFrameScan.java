package xxl.mathematica.video;

import io.vavr.control.Try;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * 遍历视频帧
 */
public class VideoFrameScan {
  /**
   * 遍历文件或地址
   *
   * @param video
   * @param f
   */
  public static void videoFrameScan(String video, Consumer<Frame> f) {
    Try.ofCallable(new Callable<Object>() {
      @Override
      public Object call() throws Exception {
        videoFrameScan(OpenCVFrameGrabber.createDefault(video), f);
        return null;
      }
    }).get();
  }

  /**
   * 遍历文件或地址
   *
   * @param video
   * @param f
   */
  public static void videoFrameScan(int video, Consumer<Frame> f) {
    Try.ofCallable(new Callable<Object>() {
      @Override
      public Object call() throws Exception {
        videoFrameScan(OpenCVFrameGrabber.createDefault(video), f);
        return null;
      }
    }).get();
  }

  /**
   * 遍历摄像头
   *
   * @param grabber
   * @param f
   * @throws FrameGrabber.Exception
   */
  private static void videoFrameScan(FrameGrabber grabber, Consumer<Frame> f) throws FrameGrabber.Exception {
    grabber.start();
    while (true) {
      Frame frame = grabber.grabFrame();
      if (frame == null) {
        break;
      }
      if (f != null) {
        f.accept(frame.clone());
      }
    }
    grabber.stop();
  }
}
