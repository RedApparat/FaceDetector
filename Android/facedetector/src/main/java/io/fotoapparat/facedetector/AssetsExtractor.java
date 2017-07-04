package io.fotoapparat.facedetector;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Extracts assets into internal storage.
 */
class AssetsExtractor {

    private final Context context;
    private final String assetFileName;

    AssetsExtractor(Context context, String assetFileName) {
        this.context = context;
        this.assetFileName = assetFileName;
    }

    /**
     * Extracts assets into internal storage. If assets were already extracted - does nothing.
     *
     * @return {@link File} which points to extracted file.
     */
    public File extractIfNeeded() {
        File extractedFile = extractedFile();

        if (extractedFile.exists()) {
            return extractedFile;
        }

        extractFileUnsafe(extractedFile);

        return extractedFile;
    }

    private void extractFileUnsafe(File extractedFile) {
        try {
            extractFile(extractedFile);
        } catch (IOException e) {
            throw new ExtractionException(e);
        }
    }

    private void extractFile(File extractedFile) throws IOException {
        InputStream assetsStream = context.getAssets().open(assetFileName);

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(extractedFile));

        try {
            byte[] buffer = new byte[256];
            while (assetsStream.read(buffer) > 0) {
                outputStream.write(buffer);
            }
        } finally {
            outputStream.flush();
            outputStream.close();

            assetsStream.close();
        }
    }

    private File extractedFile() {
        File directory = context.getExternalFilesDir(assetFileName);

        ensureValidDirectory(directory);

        return new File(directory, assetFileName);
    }

    private void ensureValidDirectory(File directory) {
        if (directory == null || !directory.exists()) {
            throw new ExtractionException("Not a valid external directory: " + directory);
        }

        if (!directory.isDirectory()) {
            throw new ExtractionException("Not a directory: " + directory);
        }
    }

    /**
     * Thrown when extraction of the file fails.
     */
    static class ExtractionException extends RuntimeException {

        ExtractionException(Throwable cause) {
            super(cause);
        }

        ExtractionException(String message) {
            super(message);
        }
    }

}
