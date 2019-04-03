package de.micromata.dokumentor.xml.classpackage.domain;

import static org.apache.commons.text.StringEscapeUtils.escapeXml10;

/** Created by Mirco Nuhn - Micromata Gmbh */
public class Einzelvertrag {

  private String titel;
  private String projectForgeID;
  private String verrechnungstagessatz;
  private String projekt;
  private String art;
  private float gesamtvolumenAlsZahl;
  private String[][] strArrTableInformation;

  /** Getter und Setter */
  public String getTitel() {
    return titel;
  }

  public String getProjekt() {
    return projekt;
  }

  public String getProjectForgeID() {
    return projectForgeID;
  }

  public String getArt() {
    return art;
  }

  public float getGesamtvolumenAlsZahl() {
    return gesamtvolumenAlsZahl;
  }

  public String getVerrechnungstagessatz() {
    return verrechnungstagessatz;
  }

  public String[][] getStrArrTableInformation() {
    return strArrTableInformation;
  }

  /**
   * Manpuliert die ProjectforgeID eines Angebots, damit im Endprodukt diese mit einer Raute
   * beginnt.
   */
  private void manipulateForgeID() {

    char first = projectForgeID.charAt(0);
    int x = first - '#';

    if (x != 0) {
      projectForgeID = "#" + projectForgeID;
    }
  }

  /** Init fuehrt alle Funktionen zur Validierung aus */
  public void init() {
    manipulateForgeID();
    escapeToXML();
  }

  public Einzelvertrag() {}

  /** Escaped Character wie "<" welche die XML-Struktur des Docx-Files zerstoeren wuerden */
  private void escapeToXML() {

    titel = escapeXml10(titel);
    projekt = escapeXml10(projekt);
    projectForgeID = escapeXml10(projectForgeID);

    for (int x = 0; x < strArrTableInformation.length; x++) {
      for (int y = 0; y < strArrTableInformation[x].length; y++) {
        escapeXml10(strArrTableInformation[x][y]);
      }
    }
  }
}
