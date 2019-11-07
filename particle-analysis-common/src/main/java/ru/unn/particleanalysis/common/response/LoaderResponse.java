package ru.unn.particleanalysis.common.response;

import ru.unn.particleanalysis.common.util.Image;

import java.util.List;

public class LoaderResponse implements Response {
    private List<Image> loaded;

    public LoaderResponse(List<Image> loaded) {
        this.loaded = loaded;
    }

    public List<Image> getLoaded() {
        return loaded;
    }

    public void setLoaded(List<Image> loaded) {
        this.loaded = loaded;
    }
}
