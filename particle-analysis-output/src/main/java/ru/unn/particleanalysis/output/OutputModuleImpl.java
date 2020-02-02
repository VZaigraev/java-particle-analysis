package ru.unn.particleanalysis.output;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import ru.unn.particleanalysis.common.OutputModule;
import ru.unn.particleanalysis.common.response.OutputResponse;
import ru.unn.particleanalysis.common.util.Contour;
import ru.unn.particleanalysis.common.util.Image;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OutputModuleImpl implements OutputModule {
    @Override
    @SuppressWarnings("unchecked")
    public OutputResponse process(Object... args) throws IOException {
        Map<Image, Map<Integer, List<Contour>>> input = (Map<Image, Map<Integer, List<Contour>>>) args[0];
        String output = (String) args[1];

        for (Map.Entry<Image, Map<Integer, List<Contour>>> e : input.entrySet()) {
            process(e.getKey(), e.getValue(), output);
        }
        return new OutputResponse();
    }

    private void process(Image image, Map<Integer, List<Contour>> distribution, String output) throws IOException {
        String fileName = (String) image.getInfo().get("File");
        String imgOutput = String.join("/", output, String.format("%s.jpg", fileName));
        Mat processedImage = (Mat) image.getInfo().get("matWithRects");
        Imgcodecs.imwrite(imgOutput, processedImage);

        CSV csv = new CSV(Arrays.asList("Bin", "Index", "Size"));
        for (Map.Entry<Integer, List<Contour>> e : distribution.entrySet()) {
            for (Contour c : e.getValue()) {
                csv.addRow(e.getKey(), c.getInfo().get("Index"), c.getInfo().get("Area"));
            }
        }
        String csvOutput = String.join("/", output, String.format("%s.csv", fileName));
        csv.to_file(csvOutput);
    }
}
