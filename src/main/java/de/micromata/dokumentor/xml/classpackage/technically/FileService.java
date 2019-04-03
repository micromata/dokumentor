package de.micromata.dokumentor.xml.classpackage.technically;

import de.micromata.dokumentor.xml.classpackage.config.FileServiceConfig;
import java.io.*;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;

/** Created by Mirco Nuhn - Micromata Gmbh */
public class FileService {

  private FileServiceConfig fileServiceConfig;

  boolean worked = false;

  /** Laedt Template-Datei */
  public void loadFile(String type) {
    File templateFile =
        new File(
            fileServiceConfig.getPathOfTemplates()
                + type
                + fileServiceConfig.getFileExtensionTypeFrom());

    // TODO mnuhn - log
    System.out.println(
        "File "
            + fileServiceConfig.getPathOfTemplates()
            + type
            + fileServiceConfig.getFileExtensionTypeFrom()
            + "exists: "
            + templateFile.exists());
  }

  /** - Ruft renameFile() auf - Docx wird zu ZIP */
  public void renameDocxToZip(String type, String title) throws IOException {
    renameFile(
        fileServiceConfig.getPathOfTemplates()
            + type
            + fileServiceConfig.getFileExtensionTypeFrom(),
        fileServiceConfig.getPathOfStorageDirectory()
            + title
            + fileServiceConfig.getFileExtensionTypeTo());
  }

  /** - Ruft renameFile() auf - ZIP wird zu Docx */
  public void renameZipToDocx(String documentName, String directoryName) throws IOException {
    renameFile(
        fileServiceConfig.getPathOfStorageDirectory()
            + directoryName
            + "/"
            + documentName
            + fileServiceConfig.getFileExtensionTypeTo(),
        fileServiceConfig.getPathOfStorageDirectory()
            + directoryName
            + "/"
            + documentName
            + fileServiceConfig.getFileExtensionTypeFrom());
    Files.deleteIfExists(
        new File(
                fileServiceConfig.getPathOfStorageDirectory()
                    + documentName
                    + fileServiceConfig.getFileExtensionTypeTo())
            .toPath());
    Files.deleteIfExists(
        new File(
                fileServiceConfig.getPathOfStorageDirectory()
                    + directoryName
                    + "/"
                    + documentName
                    + fileServiceConfig.getFileExtensionTypeTo())
            .toPath());
  }

  /**
   * Kopiert die 1. Datei anhand ihres Pfades (Samt Dateiendung) und kopiert sie mit anderer
   * Dateiendung
   */
  private void renameFile(String nameFirst, String nameSecond) throws IOException {

    worked = false;
    copyFile(nameFirst, nameSecond);

    // TODO mnuhn - log
    System.out.println("File got renamed:" + worked);
  }

  public FileService(final FileServiceConfig fileServiceConfig) {
    this.fileServiceConfig = fileServiceConfig;
  }

  /** Baut initial den OutputFolder */
  public void buildOutputFolder(String directoryName) {

    try {
      FileUtils.copyDirectory(
          new File(fileServiceConfig.getPathOfAttachements()),
          new File(fileServiceConfig.getPathOfStorageDirectory() + directoryName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Bekommt JSON-Struktur der Daten des Frontends und erstellt eine JSON-Datei mit dem JSON-String
   * als Inhalt. Diese Datei wird spaeter zum User exportiert.
   */
  public void exportToJson(String jsonStr) {

    try {
      boolean result =
          Files.deleteIfExists(new File(fileServiceConfig.getPathOfExportFile()).toPath());
      System.out.println("1. FileServiceConfig: " + fileServiceConfig.getPathOfExportFile());
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      new File(fileServiceConfig.getPathOfExportFile());

      System.out.println("2. FileServiceConfig: " + fileServiceConfig.getPathOfExportFile());

      BufferedWriter writer =
          new BufferedWriter(new FileWriter(fileServiceConfig.getPathOfExportFile(), true));
      writer.append(' ');
      writer.append(jsonStr);
      writer.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Legt JSON-Datei im OutputFolder ab */
  public void moveJsonToOutputDirectory(String outputDirectory) throws IOException {
    String source = fileServiceConfig.getPathOfExportFile();
    String dest =
        fileServiceConfig.getPathOfStorageDirectory()
            + outputDirectory
            + "/"
            + outputDirectory
            + ".json";

    copyFile(source, dest);
  }

  /** */
  private void copyFile(String source, String dest) throws IOException {

    InputStream initialStream = new FileInputStream(new File(source));
    byte[] buffer = new byte[initialStream.available()];
    initialStream.read(buffer);

    File targetFile = new File(dest);
    OutputStream outStream = new FileOutputStream(targetFile);
    outStream.write(buffer);

    worked = true;
  }
}
