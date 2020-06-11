# 和c/c++交互
# 音视频
## VideoExtractFrames 提取视频的指定位置的帧
```
void testVideoExtractFrames() {
    def files = io.vavr.collection.List.ofAll(VideoExtractFrames.videoExtractFrames("D:\\big.mp4", [333, 666, 999]))
            .zipWithIndex()
            .map(new Function<Tuple2<Frame, Integer>, String>() {
                @Override
                String apply(Tuple2<Frame, Integer> tuple2) {
                    String file = "D:\\pet" + tuple2._2 + ".jpg"
                    ImageIO.write(EnhanceFrameUtils.toBufferedImage(tuple2._1), "jpg", new File(file))
                    return file
                }
            }).asJava()
    ShowImage.showImage(files as String[])
}


```
# 图像处理
