package de.micromata.dokumentor.xml.classpackage.domain;

/** Created by Mirco Nuhn - Micromata Gmbh */
public class TitleGenerator {

  private String fileName = ""; // Dateiname fuer einzelene Dokumente
  private String rawFileName =
      ""; // Allgemein anwendbarer Teil des Dateinames für z.B. ZIP-Ordner Benennung

  /** Getter und Setter */
  public String getTitle() {
    return fileName;
  }

  public String generateZipDirectoryName() {
    return rawFileName;
  }

  /** Generiert den kompletten Dateinamen nach z.B. Faktoren wie Dokumententyp */
  public String generateTitle(String document, String forgeID, String title, String project) {

    switch (document) {
      case "angebot":
        return createName(forgeID, "angebot", project, title);

      case "festpreis_einzelvertrag_Anlage12":
        return createName(forgeID, "einzelvertrag", project, title) + "_Anlage12";
      case "dienstleistung_einzelvertrag_Anlage12":
        return createName(forgeID, "einzelvertrag", project, title) + "_Anlage12";
    }
    // TODO mnuhn - Logging Konzept überdenken
    System.out.println(
        "generateTitle() failed with following arguments: forgeID = "
            + forgeID
            + " , project = "
            + project
            + " , title = "
            + title);
    return "title_generator_has_problems";
  }

  /** Generiert den einen Teil des Dateinamens */
  private String createName(String forgeID, String type, String project, String title) {

    fileName = "";
    // TODO mnuhn - neue variable die bearbeitet wird
    title = replaceUmlauts(title);
    project = project.toUpperCase();
    project = replaceUmlauts(project);

    char first = forgeID.charAt(0);

    int x = first - '#';

    if (x == 0) {
      String newForgeID = forgeID.substring(1);
      fileName = newForgeID;
      rawFileName = newForgeID;
    } else {
      fileName = forgeID;
      rawFileName = forgeID;
    }

    rawFileName = rawFileName + "_" + project + "_" + title;
    fileName = fileName + "_" + type + "_" + project + "_" + title;

    return fileName;
  }

  /** Ersetzt Umlaute die sonst im Dateinamen stehen wuerden */
  private String replaceUmlauts(String titleWithUmlauts) {
    titleWithUmlauts = titleWithUmlauts.replaceAll(" ", "_");
    titleWithUmlauts = titleWithUmlauts.replace("ä", "ae");
    titleWithUmlauts = titleWithUmlauts.replace("ö", "oe");
    titleWithUmlauts = titleWithUmlauts.replace("ü", "ue");
    titleWithUmlauts = titleWithUmlauts.replace("Ä", "Ae");
    titleWithUmlauts = titleWithUmlauts.replace("Ö", "Oe");
    titleWithUmlauts = titleWithUmlauts.replace("Ü", "Ue");
    titleWithUmlauts = titleWithUmlauts.replace("ß", "ss");

    return titleWithUmlauts;
  }

  /** Generiert den Dateinamen fuer die Zip-Datei */
  public String generateZipDirectoryNameAsJSON() {
    String zipName = rawFileName + ".zip";
    return zipName;
  }
}
