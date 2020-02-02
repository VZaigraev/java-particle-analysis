package ru.unn.particleanalysis.particledetector;

import ru.unn.particleanalysis.common.ParticleDetectorModule;
import ru.unn.particleanalysis.common.response.ParticleDetectorResponse;
import ru.unn.particleanalysis.common.util.Contour;

import java.io.IOException;
import java.util.List;

public class ParticleDetectorModuleImpl implements ParticleDetectorModule {
    @Override
    @SuppressWarnings("unchecked")
    public ParticleDetectorResponse process(Object... args) throws IOException {
        return new ParticleDetectorResponse((List<Contour>) args[0]);
    }
}
