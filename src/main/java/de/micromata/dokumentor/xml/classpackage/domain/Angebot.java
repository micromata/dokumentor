package de.micromata.dokumentor.xml.classpackage.domain;

import static org.apache.commons.text.StringEscapeUtils.escapeXml10;

/** Created by Mirco Nuhn - Micromata Gmbh */
public class Angebot {

  private String titel;
  private String projekt;
  private String projektForgeID;
  private String version;
  private String angebotsErstellungsDatum;
  private String finaleLeistungserbringungDatum;
  private String bindungsfristDatum;
  private String ansprechpatner;
  private String projektnfa;
  private String art;

  private String[][] strArrLeistungsUmfang;
  private String[][] strArrProjektmitarbeiter;

  private float leistungsDauerInTagen;
  private float leistungsDauerInStunden;
  private float tagessatz;
  private float stundensatz;
  private float gesamtvolumenAlsZahl;

  /** Getter und Setter */
  public String getTitel() {
    return titel;
  }

  public String getProjekt() {
    return projekt;
  }

  public String getProjektForgeID() {
    return projektForgeID;
  }

  public String getVersion() {
    return version;
  }

  public String getAngebotsErstellungsDatum() {
    return angebotsErstellungsDatum;
  }

  public String getFinaleLeistungserbringungDatum() {
    return finaleLeistungserbringungDatum;
  }

  public String getBindungsfristDatum() {
    return bindungsfristDatum;
  }

  public String getAnsprechpatner() {
    return ansprechpatner;
  }

  public String[][] getStrArrLeistungsUmfang() {
    return strArrLeistungsUmfang;
  }

  public String[][] getStrArrProjektmitarbeiter() {
    return strArrProjektmitarbeiter;
  }

  public float getLeistungsDauerInTagen() {
    return leistungsDauerInTagen;
  }

  public float getLeistungsDauerInStunden() {
    return leistungsDauerInStunden;
  }

  public float getTagessatz() {
    return tagessatz;
  }

  public float getStundensatz() {
    return stundensatz;
  }

  public float getGesamtvolumenAlsZahl() {
    return gesamtvolumenAlsZahl;
  }

  public String getProjektnfa() {
    return projektnfa;
  }

  public String getArt() {
    return art;
  }

  /**
   * Manpuliert die ProjectforgeID eines Angebots, damit im Endprodukt diese mit einer Raute
   * beginnt.
   */
  private void manipulateForgeID() {

    char first = projektForgeID.charAt(0);
    int x = first - '#';

    if (x != 0) {
      projektForgeID = "#" + projektForgeID;
    }
  }

  /** Init fuehrt alle Funktionen zur Validierung aus */
  public void init() {
    validate();
    manipulateForgeID();
    escapeToXML();
  }

  public Angebot() {}

  /** Escaped Character wie "<" welche die XML-Struktur des Docx-Files zerstoeren wuerden */
  private void escapeToXML() {

    titel = escapeXml10(titel);
    projekt = escapeXml10(projekt);
    projektForgeID = escapeXml10(projektForgeID);

    version = escapeXml10(version);
    angebotsErstellungsDatum = escapeXml10(angebotsErstellungsDatum);
    finaleLeistungserbringungDatum = escapeXml10(finaleLeistungserbringungDatum);
    bindungsfristDatum = escapeXml10(bindungsfristDatum);
    ansprechpatner = escapeXml10(ansprechpatner);

    for (int x = 0; x < strArrLeistungsUmfang.length; x++) {
      for (int y = 0; y < strArrLeistungsUmfang[x].length; y++) {
        escapeXml10(strArrLeistungsUmfang[x][y]);
      }
    }

    for (int x = 0; x < strArrProjektmitarbeiter.length; x++) {
      for (int y = 0; y < strArrProjektmitarbeiter[x].length; y++) {
        escapeXml10(strArrProjektmitarbeiter[x][y]);
      }
    }
  }

  /** Validiert mit Hilfe von Regex */
  private void validate() {
    ansprechpatner = ansprechpatner.replaceAll("[^\\w- .,?!´`=&%\"#_:€]", "");

    for (int x = 0; x < strArrLeistungsUmfang.length; x++) {
      strArrLeistungsUmfang[x][0].replaceAll("[^\\w- .,?!´`=&%\"#_:€]", "");
    }

    for (int x = 0; x < strArrProjektmitarbeiter.length; x++) {
      strArrProjektmitarbeiter[x][0].replaceAll("[^\\w- .,?!´`=&%\"#_:€]", "");
    }
  }
}
