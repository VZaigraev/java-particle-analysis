package ru.unn.particleanalysis.console;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import ru.unn.particleanalysis.common.LoaderModule;
import ru.unn.particleanalysis.common.PreprocessorModule;
import ru.unn.particleanalysis.loader.LoaderModuleImpl;
import ru.unn.particleanalysis.preprocessor.PreprocessorModuleImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(final String[] args) {
//        TODO use argparse4j
//        if (args.length < 3) {
//            throw new IllegalArgumentException("Some arguments are missing.");
//        }
//
//        final String inputPath = args[1];
//        final String outputPath = args[2];
        nu.pattern.OpenCV.loadShared();
        String inputPath = "./test.data";
        String outputPath = "./";
        try {
            process(inputPath, outputPath);
        } catch (IOException e) {
            throw new RuntimeException("Exception caught during program execution", e);
        }
    }

    private static void process(final String inputPath, final String outputPath) throws IOException {
        final LoaderModule loaderModule = new LoaderModuleImpl();
        final List<Map<String, Object>> loadedImages = loaderModule.process(inputPath);
        final PreprocessorModule preprocessorModule = new PreprocessorModuleImpl();
        for (Map<String, Object> entry: loadedImages) {
            entry = preprocessorModule.process(entry);
            Imgcodecs.imwrite("./result.bmp", (Mat) entry.get("Preprocessed"));
        }
    }
}
