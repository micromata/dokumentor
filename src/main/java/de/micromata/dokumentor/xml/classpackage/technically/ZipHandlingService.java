package de.micromata.dokumentor.xml.classpackage.technically;

import de.micromata.dokumentor.xml.classpackage.config.ZipHandlingServiceConfig;
import java.io.*;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/** Created by Mirco Nuhn - Micromata Gmbh */
public class ZipHandlingService {

  private static ZipHandlingServiceConfig zipHandlingServiceConfig;
  static FileSystem fileSystem = FileSystems.getDefault();

  /** */
  public static void unzipFile(String fileToUnzip, String directoryName) throws IOException {
    FileUtils.deleteDirectory(new File(zipHandlingServiceConfig.getPathFolderToUnzip()));
    unzip(fileToUnzip, directoryName);
  }

  /** Entpackt ZIP-Datei */
  private static void unzip(String fileToUnzip, String directoryName) throws IOException {

    File file1 =
        new File(
            zipHandlingServiceConfig.getPathOfStorageDirectory()
                + fileToUnzip
                + zipHandlingServiceConfig.getFileExtensionTypeTo());

    String documentName = FilenameUtils.removeExtension(file1.getName());

    InputStream initialStream = new FileInputStream(file1.getPath());
    byte[] buffer = new byte[initialStream.available()];
    initialStream.read(buffer);

    File targetFile =
        new File(
            zipHandlingServiceConfig.getPathOfStorageDirectory()
                + directoryName
                + "/"
                + documentName
                + zipHandlingServiceConfig.getFileExtensionTypeTo());
    OutputStream outStream = new FileOutputStream(targetFile);
    outStream.write(buffer);

    ZipFile file = new ZipFile(targetFile);
    FileSystem fileSystem = FileSystems.getDefault();
    // Get file entries
    Enumeration<? extends ZipEntry> entries = file.entries();

    // Unziping files in this folder
    String uncompressedDirectory = zipHandlingServiceConfig.getPathFolderToUnzip() + "/";
    if (!Files.isDirectory(fileSystem.getPath(uncompressedDirectory))) {
      Files.createDirectory(fileSystem.getPath(uncompressedDirectory));
    }

    // Iterate over entries
    while (entries.hasMoreElements()) {
      ZipEntry entry = entries.nextElement();
      // If directory then create a new directory in uncompressed folder
      Files.deleteIfExists(fileSystem.getPath(uncompressedDirectory + entry.getName()));
      if (entry.isDirectory()) {
        // TODO mnuhn - log
        System.out.println("Creating Directory:" + uncompressedDirectory + entry.getName());
        Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
      }
      // Else create the file
      else {
        InputStream is = file.getInputStream(entry);
        BufferedInputStream bis = new BufferedInputStream(is);
        String uncompressedFileName = uncompressedDirectory + entry.getName();
        Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
        final Path tmp = uncompressedFilePath.getParent();
        if (tmp != null) // null will be returned if the path has no parent
        Files.createDirectories(tmp);
        Files.createFile(uncompressedFilePath);
        FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
        while (bis.available() > 0) {
          fileOutput.write(bis.read());
        }
        fileOutput.close();
        // TODO mnuhn - log
        System.out.println("Written :" + entry.getName());
      }
    }
  }

  /** */
  public static void zipFile(String title, String directoryName) throws Exception {

    // FileUtils.deleteQuietly(new File(zipHandlingServiceConfig.getPathOfStorageDirectory() + "/" +
    // title + zipHandlingServiceConfig.getFileExtensionTypeTo()));
    zipFolder(
        fileSystem.getPath(zipHandlingServiceConfig.getPathFolderToUnzip()),
        fileSystem.getPath(
            zipHandlingServiceConfig.getPathOfStorageDirectory()
                + directoryName
                + "/"
                + title
                + zipHandlingServiceConfig.getFileExtensionTypeTo()));
  }

  /** */
  public ZipHandlingService(final ZipHandlingServiceConfig zipHanlingServiceConfig) {
    this.zipHandlingServiceConfig = zipHanlingServiceConfig;
  }

  /** Baut den Path für die ZIP-Datei zusammen und triggerd zipFolder() */
  public void buildFinalZip(String directoryName) {
    String zipFile =
        zipHandlingServiceConfig.getPathOfStorageDirectory()
            + directoryName
            + zipHandlingServiceConfig.getFileExtensionTypeTo();

    try {
      zipFolder(
          fileSystem.getPath(zipHandlingServiceConfig.getPathOfStorageDirectory() + directoryName),
          fileSystem.getPath(zipFile));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Erstellt mit java.util.zip eine ZIP-Datei */
  private static void zipFolder(final Path sourceFolderPath, Path zipPath) throws Exception {

    final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
    Files.walkFileTree(
        sourceFolderPath,
        new SimpleFileVisitor<Path>() {
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
            Files.copy(file, zos);
            zos.closeEntry();
            return FileVisitResult.CONTINUE;
          }
        });
    zos.close();
  }

  /** Ueberprueft ob eine Datei bereits existiert */
  private boolean doesFileExists(String path_to_file) {
    File f = new File(path_to_file);
    if (f.exists() && !f.isDirectory()) {
      return true;
    }
    return false;
  }
}
