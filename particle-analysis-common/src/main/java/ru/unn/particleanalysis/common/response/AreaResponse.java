package ru.unn.particleanalysis.common.response;

import ru.unn.particleanalysis.common.util.Contour;

import java.util.List;

public class AreaResponse implements Response {
    private List<Contour> processed;

    public AreaResponse(List<Contour> processed) {
        this.processed = processed;
    }

    public List<Contour> getProcessed() {
        return processed;
    }

    public void setProcessed(List<Contour> processed) {
        this.processed = processed;
    }
}
