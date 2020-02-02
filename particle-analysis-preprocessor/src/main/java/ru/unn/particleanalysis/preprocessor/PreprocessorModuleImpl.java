package ru.unn.particleanalysis.preprocessor;

import org.apache.commons.collections4.CollectionUtils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import ru.unn.particleanalysis.common.PreprocessorModule;
import ru.unn.particleanalysis.common.response.PreprocessorResponse;
import ru.unn.particleanalysis.common.util.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreprocessorModuleImpl implements PreprocessorModule {
    @SuppressWarnings("unchecked")
    @Override
    public PreprocessorResponse process(final Object... args) throws IOException {
        final List<Image> images = (List<Image>) args[0];
        final List<Image> processed = new ArrayList<>();

//        TODO probably use parallel streams
        for (Image image : images) {
            CollectionUtils.addIgnoreNull(processed, processImage(image));
        }

        return new PreprocessorResponse(processed);
    }

    private Image processImage(final Image raw) {
        Mat img = raw.getMat().clone();
        img = img.rowRange(0, img.rows() - 60);
        Imgproc.pyrDown(img, img);
        Imgproc.blur(img, img, new Size(3, 3));
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        return new Image(img, raw.getInfo());
    }
}
