package xxl.mathematica.video;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.core.Mat;

public class EnhanceFrameUtils extends Java2DFrameUtils {
    private static OpenCVFrameConverter.ToOrgOpenCvCoreMat coreMatConv = new OpenCVFrameConverter.ToOrgOpenCvCoreMat();

    public synchronized static Mat toCoreMat(Frame src) {
        return coreMatConv.convert(src).clone();
    }
}
