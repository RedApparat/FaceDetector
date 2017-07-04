package io.fotoapparat.facedetector;

import android.content.Context;

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

        FileOutputStream outputStream = new FileOutputStream(extractedFile);

        try {
            final byte[] buffer = new byte[8192];
            for (int r; (r = assetsStream.read(buffer)) != -1; ) {
                outputStream.write(buffer, 0, r);
            }
        } finally {
            outputStream.flush();
            outputStream.close();

            assetsStream.close();
        }
    }

    private File extractedFile() {
        File directory = context.getExternalFilesDir(null);

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
