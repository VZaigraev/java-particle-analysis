package ru.unn.particleanalysis.common;

import ru.unn.particleanalysis.common.response.Response;

import java.io.IOException;

public interface Module<R extends Response> {
    R process(Object... args) throws IOException;

}
