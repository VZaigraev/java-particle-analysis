package ru.unn.particleanalysis.common.response;

import ru.unn.particleanalysis.common.util.Contour;
import ru.unn.particleanalysis.common.util.Image;

import java.util.List;
import java.util.Map;

public class DistributionResponse implements Response {
    private Map<Image, Map<Integer, List<Contour>>> distributions;

    public DistributionResponse(Map<Image, Map<Integer, List<Contour>>> distributions) {
        this.distributions = distributions;
    }

    public Map<Image, Map<Integer, List<Contour>>> getDistributions() {
        return distributions;
    }
}
