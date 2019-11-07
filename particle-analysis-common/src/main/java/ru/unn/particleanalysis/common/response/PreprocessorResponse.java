package ru.unn.particleanalysis.common.response;

import ru.unn.particleanalysis.common.util.Image;

import java.util.List;

public class PreprocessorResponse implements Response {
    private List<Image> processed;

    public PreprocessorResponse(List<Image> processed) {
        this.processed = processed;
    }

    public List<Image> getProcessed() {
        return processed;
    }

    public void setProcessed(List<Image> processed) {
        this.processed = processed;
    }
}
