package ru.unn.particleanalysis.loader;

import org.apache.commons.collections4.CollectionUtils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.unn.particleanalysis.common.LoaderModule;
import ru.unn.particleanalysis.common.response.LoaderResponse;
import ru.unn.particleanalysis.common.util.Image;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoaderModuleImpl implements LoaderModule {
    private static final Pattern REGEXP = Pattern.compile("(.*) ([0-9]*)");

    @Override
    public LoaderResponse process(final Object... args) throws IOException {
        final String input = (String) args[0];
        final File infoFile = new File(input);
        if (!infoFile.exists()) {
            throw new IllegalArgumentException(String.format("Info file doesn't exist: %s}", infoFile));
        }

        List<Image> images = new ArrayList<>();

        for (String line : Files.readAllLines(infoFile.toPath())) {
            CollectionUtils.addIgnoreNull(images, processLine(line));
        }
        return new LoaderResponse(images);
    }

    private Image processLine(final String line) {
        final Matcher matcher = REGEXP.matcher(line);
        Image result = null;
        if (matcher.matches()) {
            final String imageFilePath = matcher.group(1);
            if (!new File(imageFilePath).exists()) {
//                TODO use normal logging
                System.out.println(String.format("Image file doesn't exist: %s", imageFilePath));
            } else {
                final Mat image = Imgcodecs.imread(imageFilePath, Imgcodecs.IMREAD_COLOR);
                final Map<String, Object> info = new HashMap<>();
                final int imageDimension = Integer.parseInt(matcher.group(2));
//                TODO move to constants file or enum
                info.put("Dimension", imageDimension);
                info.put("File", imageFilePath);
                result = new Image(image, info);
            }
        }
        return result;
    }

}
