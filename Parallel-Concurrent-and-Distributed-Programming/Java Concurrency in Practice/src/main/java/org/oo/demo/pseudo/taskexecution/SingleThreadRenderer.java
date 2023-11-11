package org.oo.demo.pseudo.taskexecution;

import com.sun.scenario.effect.ImageData;
import org.oo.demo.model.ImageInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleThreadRenderer {

    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<>();
        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            imageData.add(imageInfo.downloadImage());
        }
        for (ImageData data : imageData) {
            renderImage(data);
        }
    }

    private void renderText(CharSequence source) {
        return;
    }

    private List<ImageInfo> scanForImageInfo(CharSequence source) {
        return Collections.emptyList();
    }

    private void renderImage(ImageData data) {
        return;
    }
}
