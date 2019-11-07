package ru.unn.particleanalysis.common.util;

import org.opencv.core.Mat;

import java.util.HashMap;
import java.util.Map;

public class Image {
    private final Mat mat;
    private final Map<String, Object> info;

    public Image(Mat mat, Map<String, Object> info) {
        this.mat = mat;
        this.info = info == null ? new HashMap<>() : info;
    }

    public Mat getMat() {
        return mat;
    }

    public Map<String, Object> getInfo() {
        return info;
    }
}
