package ru.unn.particleanalysis.common.util;

import org.opencv.core.MatOfPoint;

import java.util.HashMap;
import java.util.Map;

public class Contour {
    private final Image image;
    private final MatOfPoint contour;
    private final Map<String, Object> info;

    public Contour(Image image, MatOfPoint contour, Map<String, Object> info) {
        this.image = image;
        this.contour = contour;
        this.info = info == null ? new HashMap<>() : info;
    }

    public Image getImage() {
        return image;
    }

    public MatOfPoint getContour() {
        return contour;
    }

    public Map<String, Object> getInfo() {
        return info;
    }
}
