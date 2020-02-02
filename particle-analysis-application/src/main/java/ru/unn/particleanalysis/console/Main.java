package ru.unn.particleanalysis.console;

import ru.unn.particleanalysis.area.AreaModuleImpl;
import ru.unn.particleanalysis.clusterdetector.ClusterDetectorModuleImpl;
import ru.unn.particleanalysis.common.*;
import ru.unn.particleanalysis.common.response.*;
import ru.unn.particleanalysis.distribution.DistributionModuleImpl;
import ru.unn.particleanalysis.loader.LoaderModuleImpl;
import ru.unn.particleanalysis.output.OutputModuleImpl;
import ru.unn.particleanalysis.particledetector.ParticleDetectorModuleImpl;
import ru.unn.particleanalysis.preprocessor.PreprocessorModuleImpl;

import java.io.IOException;

public class Main {
    private static final LoaderModule loaderModule = new LoaderModuleImpl();
    private static final PreprocessorModule preprocessorModule = new PreprocessorModuleImpl();
    private static final ClusterDetectorModule clusterDetectorModule = new ClusterDetectorModuleImpl();
    private static final ParticleDetectorModule particleDetectorModule = new ParticleDetectorModuleImpl();
    private static final AreaModule areaModule = new AreaModuleImpl();
    private static final DistributionModule distributionModule = new DistributionModuleImpl();
    private static final OutputModule outputModule = new OutputModuleImpl();

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
        LoaderResponse loaderResponse = loaderModule.process(inputPath);
        PreprocessorResponse preprocessorResponse = preprocessorModule.process(loaderResponse.getLoaded());
        ClusterDetectorResponse clusterDetectorResponse = clusterDetectorModule.process(preprocessorResponse.getProcessed());
        ParticleDetectorResponse particleDetectorResponse = particleDetectorModule.process(clusterDetectorResponse.getProcessed());
        AreaResponse areaResponse = areaModule.process(particleDetectorResponse.getProcessed());
        DistributionResponse distributionResponse = distributionModule.process(areaResponse.getProcessed());
        outputModule.process(distributionResponse.getDistributions(), outputPath);
    }
}
