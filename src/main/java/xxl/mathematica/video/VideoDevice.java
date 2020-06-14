package xxl.mathematica.video;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;

import java.util.List;

/**
 * 摄像头设备
 */
public class VideoDevice {
  /**
   * 获取摄像头设备信息
   *
   * @return
   * @throws FrameGrabber.Exception
   */
  public static List<String> videoDevice() throws FrameGrabber.Exception {
    return io.vavr.collection.List.of(VideoInputFrameGrabber.getDeviceDescriptions())
        .asJava();
  }
}
