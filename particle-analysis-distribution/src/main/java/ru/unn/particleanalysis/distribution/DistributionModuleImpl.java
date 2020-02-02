package ru.unn.particleanalysis.distribution;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import ru.unn.particleanalysis.common.DistributionModule;
import ru.unn.particleanalysis.common.response.DistributionResponse;
import ru.unn.particleanalysis.common.util.Contour;
import ru.unn.particleanalysis.common.util.Image;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DistributionModuleImpl implements DistributionModule {
    private int chooseBin(Contour c) {
        return (int) (((double) c.getInfo().get("Area")) / 10);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DistributionResponse process(final Object... args) throws IOException {
        final List<Contour> contours = (List<Contour>) args[0];
        Map<Image, List<Contour>> grouped = contours.stream().collect(Collectors.groupingBy(Contour::getImage));

        Map<Image, Map<Integer, List<Contour>>> result = new HashMap<>();
        for (Map.Entry<Image, List<Contour>> e : grouped.entrySet()) {
            result.put(e.getKey(), process(e.getKey(), e.getValue()));
        }

        return new DistributionResponse(result);
    }

    private Map<Integer, List<Contour>> process(final Image image, final List<Contour> contours) {
        Mat copy = image.getMat().clone();
        Map<Integer, List<Contour>> distribution = contours.stream().collect(Collectors.groupingBy(this::chooseBin));
        for (Contour c : contours) {
            Rect rect = Imgproc.boundingRect(c.getContour());
            Imgproc.rectangle(copy, rect.tl(), rect.br(), new Scalar(0, 255, 0));
            String index = String.valueOf(c.getInfo().get("Index"));
            //Imgproc.putText(copy, index, rect.tl(), Core.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 0, 0));
        }
        image.getInfo().put("matWithRects", copy);
        return distribution;
    }
}
