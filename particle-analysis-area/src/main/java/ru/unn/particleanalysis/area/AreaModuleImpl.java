package ru.unn.particleanalysis.area;

import org.apache.commons.collections4.CollectionUtils;
import org.opencv.imgproc.Imgproc;
import ru.unn.particleanalysis.common.AreaModule;
import ru.unn.particleanalysis.common.response.AreaResponse;
import ru.unn.particleanalysis.common.response.ClusterDetectorResponse;
import ru.unn.particleanalysis.common.util.Contour;
import ru.unn.particleanalysis.common.util.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AreaModuleImpl implements AreaModule {
    @Override
    @SuppressWarnings("unchecked")
    public AreaResponse process(Object... args) throws IOException {
        final List<Contour> contours = (List<Contour>) args[0];
        final List<Contour> processed = new ArrayList<>();

//        TODO probably use parallel streams
        for (Contour contour : contours) {
            CollectionUtils.addIgnoreNull(processed, processContour(contour));
        }

        return new AreaResponse(processed);
    }

    private Contour processContour(final Contour contour) {
        double area = Imgproc.contourArea(contour.getContour());
        contour.getInfo().put("Area", area);
        return contour;
    }

}
