package xxl.mathematica.video

class VideoDeviceTest extends GroovyTestCase {
    void testVideoDevice() {
        def devices = VideoDevice.videoDevice()
        System.err.println(devices)
    }
}
