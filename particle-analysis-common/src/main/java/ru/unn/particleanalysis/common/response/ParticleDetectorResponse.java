package ru.unn.particleanalysis.common.response;

import ru.unn.particleanalysis.common.util.Contour;

import java.util.List;

public class ParticleDetectorResponse implements Response {
    private List<Contour> processed;

    public ParticleDetectorResponse(List<Contour> processed) {
        this.processed = processed;
    }

    public List<Contour> getProcessed() {
        return processed;
    }

    public void setProcessed(List<Contour> processed) {
        this.processed = processed;
    }
}
