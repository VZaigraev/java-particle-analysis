package ru.unn.particleanalysis.clusterdetector;

import org.apache.commons.collections4.CollectionUtils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import ru.unn.particleanalysis.common.ClusterDetectorModule;
import ru.unn.particleanalysis.common.response.ClusterDetectorResponse;
import ru.unn.particleanalysis.common.util.Contour;
import ru.unn.particleanalysis.common.util.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.opencv.core.Core.magnitude;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8UC1;

public class ClusterDetectorModuleImpl implements ClusterDetectorModule {
    @Override
    @SuppressWarnings("unchecked")
    public ClusterDetectorResponse process(Object... args) throws IOException {
        final List<Image> images = (List<Image>) args[0];
        final List<Contour> processed = new ArrayList<>();

//        TODO probably use parallel streams
        for (Image image : images) {
            CollectionUtils.addAll(processed, processImage(image));
        }

        return new ClusterDetectorResponse(processed);
    }

    private List<Contour> processImage(final Image image) {
        Mat copy = image.getMat().clone();
        Mat edges = copy.clone();
        Imgproc.Canny(copy, edges, 100, 200);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        List<Contour> result = new ArrayList<>();
        int counter = 0;
        for (MatOfPoint mat : contours) {
            Contour c = new Contour(image, mat, new HashMap<>());
            c.getInfo().put("Index", counter++);
            result.add(c);
        }
        return result;
    }
}
