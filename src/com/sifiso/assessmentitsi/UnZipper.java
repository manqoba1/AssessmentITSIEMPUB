package com.sifiso.assessmentitsi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author sifiso.mtshweni
 * @This class is being borrowed from:
 * @author Subhomoy Haldar
 *
 * Had to update some code to create folders
 */
public final class UnZipper {

    /**
     * The maximum number of bytes read per iteration from the ZipInputStream.
     */
    private static final int BUFFER_SIZE = 4096; // in bytes

    // Prevent instantiation
    private UnZipper() {
    }

    /**
     * This method is used to unzip a ZIP file specified by the <i>full</i>
     * path given in the first {@code String} to a directory, whose
     * <i>full</i> path is given in the second {@code String}.
     * <p>
     * The <b>default charset</b> of the system is utilized.
     * </p>
     *
     * @param source The <i>full</i> path of the ZIP (or JAR or EPUB) file.
     * @param destination The <i>full</i> path of the destination folder.
     * @throws IOException If a read/write error occurs.
     * @see Charset#defaultCharset()
     */
    public static void unzip(String source, String destination)
            throws IOException {
        unzip(new File(source), new File(destination));
    }

    /**
     * This method is used to unzip a ZIP file specified by the <i>full</i>
     * path given in the first {@code String} to a directory, whose
     * <i>full</i> path is given in the second {@code String}, whilst making use
     * of the {@code Charset} specified.
     *
     * @param source The <i>full</i> path of the ZIP (or JAR or EPUB) file.
     * @param destination The <i>full</i> path of the destination folder.
     * @param charset The {@code Charset} to be used during extraction.
     * @throws IOException IOException If a read/write error occurs.
     * @see Charset
     * @see Charset#forName(String)
     */
    public static void unzip(String source, String destination, Charset charset)
            throws IOException {

        unzip(new File(source), new File(destination), charset);
    }

    /**
     * This method is used to extract the ZIP file represented by the first
     * argument. The second parameter is the File that represents the
     * destination directory.
     * <p>
     * The <b>default charset</b> of the system is utilized.
     * </p>
     *
     * @param source The {@code File} representing the ZIP file.
     * @param destination The {@code File} representing the destination folder.
     * @throws IOException If a read/write error occurs.
     */
    public static void unzip(File source, File destination)
            throws IOException {
        unzip(source, destination, Charset.defaultCharset());
    }

    /**
     * This method is used to extract the ZIP file represented by the first
     * argument. The second parameter is the File that represents the
     * destination directory. The given charset is used during extraction.
     *
     * @param source The {@code File} representing the ZIP file.
     * @param destination The {@code File} representing the destination folder.
     * @param charset The {@code Charset} to be used during extraction.
     * @throws IOException If a read/write error occurs.
     */
    public static void unzip(File source, File destination, Charset charset)
            throws IOException {

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(source), charset)) {

            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                File file = new File(destination, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdir();
                } else {
                    File parent = file.getParentFile();
                    parent.canExecute();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    extractFile(zipIn, file);
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }

        }

    }

    /**
     * This method extracts a file in a ZIP archive to the given destination
     * file.
     *
     * @param zipIn The ZipInputStream (source).
     * @param file The File (destination).
     * @throws IOException If read/write error occurs.
     */
    private static void extractFile(ZipInputStream zipIn, File file)
            throws IOException {

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int location;
            while ((location = zipIn.read(buffer)) != -1) {
                outputStream.write(buffer, 0, location);
            }
        }
    }
}
