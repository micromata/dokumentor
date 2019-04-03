package de.micromata.dokumentor.xml.classpackage.config;

import java.io.File;

/**
 * Created by Mirco Nuhn - Micromata Gmbh
 *
 * <p>Hier werden.. .. Pfade zum speichern von Dateien auf dem Server definiert .. Die "FILE_TYPE"
 * definiert .. Git Zugangsdaten fuer das Dokumenten-Git hinterlegt .. XML Schluesselwoerter
 * hinterlegt, welche spaeter im Template ersetzt werden sollen .. XML Schnipsel fuer die korrekte
 * Erstellung von Tabellen in einem Docx-File hinterlegt
 */
public class Config
    implements FileServiceConfig,
        GitServiceConfig,
        XmlEditingServiceConfig,
        ZipHandlingServiceConfig,
        MainServiceConfig {

  public enum DocumentType {
    ANGEBOT,
    EINZELVERTRAG_FESTPREIS,
    EINZELVERTRAG_DIENSTLEISTUNG;

    @Override
    public String toString() {
      switch (this) {
        case ANGEBOT:
          return "angebot";
        case EINZELVERTRAG_FESTPREIS:
          return "festpreis_einzelvertrag_Anlage12";
        case EINZELVERTRAG_DIENSTLEISTUNG:
          return "dienstleistung_einzelvertrag_Anlage12";
        default:
          throw new IllegalArgumentException();
      }
    }
  }

  // For Mirco Local Use
  private static final String PATH_MAIN = "/Users/mnuhn/Documents/Repositories/Documentgenerator/";

  // For Server Use
  // private static final String PATH_MAIN = "/home/ag/Documentgenerator/";

  private static final String PATH_TEMP = PATH_MAIN + "erzeugnisse/";
  private static final String PATH_SOURCE =
      PATH_MAIN + "repo_angebotsvorlagen-dhl/angebotsvorlagen-dhl/";
  private static final String PATH_ID = PATH_MAIN + "id_rsa/";

  private static final String PATH_FOLDER_TO_UNZIP = PATH_TEMP + "uncompressed";
  // All or Main Service
  private static final String TEMPLATE = PATH_SOURCE + "templates/template_";
  private static final String ATTACHMENTS = PATH_SOURCE + "attachments";
  private static final String EXPORTFILENAME = "export.json";

  private static final String EXPORTFILE = PATH_TEMP + EXPORTFILENAME;
  // File Service
  private static final String FILE_TYPE_FROM = ".docx";

  private static final String FILE_TYPE_TO = ".zip";

  // Git Service
  private static final String GIT_LOGIN_USERNAME = "";
  private static final String GIT_LOGIN_PASSWORD = "passwort";
  private static final String GIT_PRIV_SSH_ID_DOCUMENTGENERATOR = PATH_ID + "id_documentgenerator";
  private static final String GIT_PUB_SSH_ID_DOCUMENTGENERATOR =
      PATH_ID + "id_documentgenerator.pub";
  private static final String GIT_URL_OF_REPO =
      "ssh://git2.micromata.de:7999/buyellow/angebotsvorlagen-dhl.git";
  private static final String GIT_REMOTE = "origin";
  private static final String GIT_TAG = "master";
  private static final String GIT_FILE_REPOSITORY_PATH = PATH_MAIN + "repo_angebotsvorlagen-dhl";
  private static final File GIT_FILE_REPOSITORY = new File(PATH_MAIN + "repo_angebotsvorlagen-dhl");

  private static final int GIT_PORT = 7999;

  // XML Editing Service

  private static final String PATH_WORD_DOCUMENT_XML = PATH_FOLDER_TO_UNZIP + "/word/document.xml";

  private static final String PATH_WORD_FOOTER_XML = PATH_FOLDER_TO_UNZIP + "/word/footer1.xml";

  // new Keywords
  private static final String XML_KEYWORD_TITEL = "#Titel";
  private static final String XML_KEYWORD_TABELLEANTEILE = "#TabelleAnteile";
  private static final String XML_KEYWORD_TABELLELIEFERTERMINE = "#TabelleLiefertermine";
  private static final String XML_KEYWORD_GESAMTVOLUMENALSZAHL = "#GesamtvolumenAlsZahl";
  private static final String XML_KEYWORD_GESAMTVOLUMENALSWORT = "#GesamtvolumenAlsWort";

  private static final String XML_KEYWORD_VERRECHNUNGSTAGESSATZ = "#Verrechnungstagessatz";

  private static final String XML_KEYWORD_PROJEKT = "#Projekt";
  private static final String XML_KEYWORD_PROJECTFOGEID = "#ProjectForgeID";
  private static final String XML_KEYWORD_VERSION = "#Version";
  private static final String XML_KEYWORD_ANGEBOTSERSTELLUNGSDATUM = "#AngebotsErstellungsDatum";
  private static final String XML_KEYWORD_FINALELEISTUNGSERBRINGUNGSDATUM =
      "#FinalesLeistungserbringungDatum";
  private static final String XML_KEYWORD_TABELLELEISTUNGSERBRINGUNG = "#TabelleLeistungsUmfang";
  private static final String XML_KEYWORD_LEISTUNGSDAUERINTAGEN = "#LeistungsDauerInTagen";
  private static final String XML_KEYWORD_ANSPRECHPATNER = "#Ansprechpartner";
  private static final String XML_KEYWORD_TABELLEPROJEKTMITARBEITER = "#TabelleProjektmitarbeiter";
  private static final String XML_KEYWORD_LEISTUNGSDAUERINSTUNDEN = "#LeistungsDauerInStunden";
  private static final String XML_KEYWORD_STUNDENSATZ = "#Stundensatz";
  private static final String XML_KEYWORD_TAGESSATZ = "#Tagessatz";
  private static final String XML_KEYWORD_BINDUNGSFRISTDATUM = "#BindungsfristDatum";
  private static final String XML_KEYWORD_VERTRAGSART = "#Vertragsart";
  private static final String XML_KEYWORD_PROJEKTNFA = "#ProjektNFA";

  private static final String ANGEBOT_ART_FESTPREIS_TEXT =
      "Die Leistungen werden als Festpreis angeboten.";
  private static final String ANGEBOT_ART_DIENSTLEISTUNG_TEXT =
      "Die Leistungen werden nach Aufwand abgerechnet.";

  // std. Keywords
  private static final String TABLE_DATE_HEADER =
      "<w:tbl><w:tblPr><w:tblW w:w=\"0\" w:type=\"auto\"/><w:tblInd w:w=\"529\" w:type=\"dxa\"/><w:tblLayout w:type=\"fixed\"/> <w:tblLook w:val=\"0000\" w:firstRow=\"0\" w:lastRow=\"0\" w:firstColumn=\"0\" w:lastColumn=\"0\" w:noHBand=\"0\" w:noVBand=\"0\"/> </w:tblPr><w:tblGrid><w:gridCol w:w=\"567\"/><w:gridCol w:w=\"6804\"/><w:gridCol w:w=\"2451\"/></w:tblGrid><w:tr w:rsidR=\"00517237\"><w:tc><w:tcPr><w:tcW w:w=\"567\" w:type=\"dxa\"/><w:tcBorders><w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/></w:tcBorders></w:tcPr><w:p w:rsidR=\"00517237\" w:rsidRDefault=\"00517237\"><w:pPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/></w:pPr><w:r><w:t>Nr.</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"6804\" w:type=\"dxa\"/><w:tcBorders><w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/></w:tcBorders></w:tcPr><w:p w:rsidR=\"00517237\" w:rsidRDefault=\"00517237\"><w:pPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/></w:pPr><w:r><w:t>Meilenstein</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"2451\" w:type=\"dxa\"/><w:tcBorders><w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:right w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/></w:tcBorders></w:tcPr><w:p w:rsidR=\"00517237\" w:rsidRDefault=\"00517237\"><w:pPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/></w:pPr><w:r><w:t>Termin</w:t></w:r></w:p></w:tc></w:tr>";

  private static final String TABLE_PROPORTION_HEADER =
      "<w:tbl><w:tblPr><w:tblW w:w=\"0\" w:type=\"auto\"/><w:tblInd w:w=\"534\" w:type=\"dxa\"/><w:tblBorders><w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/><w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/><w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/><w:right w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/><w:insideH w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/><w:insideV w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"auto\"/></w:tblBorders><w:tblLook w:val=\"01E0\" w:firstRow=\"1\" w:lastRow=\"1\" w:firstColumn=\"1\" w:lastColumn=\"1\" w:noHBand=\"0\" w:noVBand=\"0\"/></w:tblPr><w:tblGrid><w:gridCol w:w=\"567\"/><w:gridCol w:w=\"7371\"/><w:gridCol w:w=\"1134\"/></w:tblGrid><w:tr w:rsidR=\"008B2B05\" w:rsidRPr=\"00FD3BE4\" w:rsidTr=\"008A20FC\"><w:tc><w:tcPr><w:tcW w:w=\"567\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"008B2B05\" w:rsidRPr=\"00FD3BE4\" w:rsidRDefault=\"008B2B05\" w:rsidP=\"008A20FC\"><w:pPr><w:pStyle w:val=\"Standardnummeriert\"/><w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\"0\"/></w:numPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/></w:pPr><w:r w:rsidRPr=\"00FD3BE4\"><w:t>Nr.</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"7371\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"008B2B05\" w:rsidRPr=\"00FD3BE4\" w:rsidRDefault=\"008B2B05\" w:rsidP=\"008A20FC\"><w:pPr><w:pStyle w:val=\"Standardnummeriert\"/><w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\"0\"/></w:numPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/></w:pPr><w:r w:rsidRPr=\"00FD3BE4\"><w:t>Meilenstein</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1134\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"008B2B05\" w:rsidRPr=\"00FD3BE4\" w:rsidRDefault=\"008B2B05\" w:rsidP=\"008A20FC\"><w:pPr><w:pStyle w:val=\"Standardnummeriert\"/><w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\"0\"/></w:numPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/></w:pPr><w:r><w:t xml:space=\"preserve\">Anteil </w:t></w:r></w:p></w:tc></w:tr>";

  private static final String TABLE_LEISTUNGSUMFANG_HEADER =
      "<w:tbl><w:tblPr><w:tblStyle w:val=\"Tabellenraster\"/><w:tblW w:w=\"0\" w:type=\"auto\"/><w:tblLook w:val=\"04A0\" w:firstRow=\"1\" w:lastRow=\"0\" w:firstColumn=\"1\" w:lastColumn=\"0\" w:noHBand=\"0\" w:noVBand=\"1\"/></w:tblPr><w:tblGrid><w:gridCol w:w=\"6382\"/><w:gridCol w:w=\"2679\"/></w:tblGrid><w:tr w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidTr=\"00B577C3\"><w:tc><w:tcPr><w:tcW w:w=\"6382\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr><w:t>Beschreibung</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"2679\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr><w:t>Leistungstage</w:t></w:r></w:p></w:tc></w:tr>";

  private static final String TABLE_PROJEKTMITARBEITER_HEADER =
      "<w:tbl><w:tblPr><w:tblStyle w:val=\"Tabellenraster\"/><w:tblW w:w=\"0\" w:type=\"auto\"/><w:tblLook w:val=\"04A0\" w:firstRow=\"1\" w:lastRow=\"0\" w:firstColumn=\"1\" w:lastColumn=\"0\" w:noHBand=\"0\" w:noVBand=\"1\"/></w:tblPr><w:tblGrid><w:gridCol w:w=\"4531\"/><w:gridCol w:w=\"2377\"/><w:gridCol w:w=\"2153\"/></w:tblGrid><w:tr w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidTr=\"003C4C18\"><w:tc><w:tcPr><w:tcW w:w=\"4531\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr><w:t>Name</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"2377\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr><w:t>Leistungsumfang in LT</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"2153\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"003C4C18\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/><w:b/></w:rPr><w:t xml:space=\"preserve\">Leistungsumfang in Stunden</w:t></w:r></w:p></w:tc></w:tr>";
  private static final String TABLE_LEISTUNGSUMFANG_COLUMN_1 =
      "<w:tr w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidTr=\"00B577C3\"><w:trPr><w:trHeight w:val=\"260\"/></w:trPr><w:tc><w:tcPr><w:tcW w:w=\"6382\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr><w:t>";
  private static final String TABLE_LEISTUNGSUMFANG_COLUMN_2 =
      "</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"2679\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr><w:t>";

  private static final String TABLE_LEISTUNGSUMFANG_COLUMN_3 = "</w:t></w:r></w:p></w:tc></w:tr>";
  private static final String TABLE_PROJEKTMITARBEITER_COLUMN_1 =
      "<w:tr w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidTr=\"003C4C18\"><w:tc><w:tcPr><w:tcW w:w=\"4531\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr><w:t xml:space=\"preserve\">";
  private static final String TABLE_PROJEKTMITARBEITER_COLUMN_2 =
      "</w:t></w:r><w:proofErr w:type=\"spellEnd\"/></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"2377\" w:type=\"dxa\"/><w:vAlign w:val=\"bottom\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr><w:t>";
  private static final String TABLE_PROJEKTMITARBEITER_COLUMN_3 =
      "</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"2153\" w:type=\"dxa\"/><w:vAlign w:val=\"bottom\"/></w:tcPr><w:p w:rsidR=\"00E12CDC\" w:rsidRPr=\"00E12CDC\" w:rsidRDefault=\"00E12CDC\" w:rsidP=\"00E12CDC\"><w:pPr><w:pStyle w:val=\"Absatztext\"/><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr></w:pPr><w:r w:rsidRPr=\"00E12CDC\"><w:rPr><w:rFonts w:ascii=\"Verdana\" w:hAnsi=\"Verdana\"/></w:rPr><w:t>";

  private static final String TABLE_PROJEKTMITARBEITER_COLUMN_4 =
      "</w:t></w:r></w:p></w:tc></w:tr>";
  private static final String TABLE_DATE_COLUMN_1 =
      "<w:tr w:rsidR=\"00643E3A\"><w:tc><w:tcPr><w:tcW w:w=\"567\" w:type=\"dxa\"/><w:tcBorders><w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/></w:tcBorders></w:tcPr><w:p w:rsidR=\"00643E3A\" w:rsidRDefault=\"00643E3A\"><w:pPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/></w:pPr><w:r><w:t>";
  private static final String TABLE_DATE_COLUMN_2 =
      "</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"6804\" w:type=\"dxa\"/><w:tcBorders><w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/></w:tcBorders></w:tcPr><w:p w:rsidR=\"00643E3A\" w:rsidRDefault=\"00643E3A\"><w:pPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/><w:rPr><w:color w:val=\"FF0000\"/></w:rPr></w:pPr><w:r><w:rPr><w:color w:val=\"FF0000\"/></w:rPr><w:t xml:space=\"preserve\">";
  private static final String TABLE_DATE_COLUMN_3 =
      "</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"2451\" w:type=\"dxa\"/><w:tcBorders><w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/><w:right w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/></w:tcBorders></w:tcPr><w:p w:rsidR=\"00643E3A\" w:rsidRDefault=\"00D11D8E\"><w:pPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/><w:rPr><w:color w:val=\"FF0000\"/></w:rPr></w:pPr><w:r><w:rPr><w:color w:val=\"FF0000\"/></w:rPr><w:t>";

  private static final String TABLE_DATE_COLUMN_4 = "</w:t></w:r></w:p></w:tc></w:tr>";
  private static final String TABLE_PROPORTION_COLUMN_1 =
      "<w:tr w:rsidR=\"00D11D8E\" w:rsidTr=\"008A20FC\"><w:tc><w:tcPr><w:tcW w:w=\"567\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00D11D8E\" w:rsidRDefault=\"00D11D8E\" w:rsidP=\"00D11D8E\"><w:pPr><w:pStyle w:val=\"Standardnummeriert\"/><w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\"0\"/></w:numPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/></w:pPr><w:r><w:t>";
  private static final String TABLE_PROPORTION_COLUMN_2 =
      "</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"7371\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00D11D8E\" w:rsidRDefault=\"00D11D8E\" w:rsidP=\"00D11D8E\"><w:pPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/><w:rPr><w:color w:val=\"FF0000\"/></w:rPr></w:pPr><w:r><w:rPr><w:color w:val=\"FF0000\"/></w:rPr><w:t xml:space=\"preserve\">";
  private static final String TABLE_PROPORTION_COLUMN_3 =
      "</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"1134\" w:type=\"dxa\"/></w:tcPr><w:p w:rsidR=\"00D11D8E\" w:rsidRPr=\"00EE6511\" w:rsidRDefault=\"00D11D8E\" w:rsidP=\"00D11D8E\"><w:pPr><w:pStyle w:val=\"Standardnummeriert\"/><w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\"0\"/></w:numPr><w:spacing w:line=\"276\" w:lineRule=\"auto\"/><w:rPr><w:color w:val=\"FF0000\"/></w:rPr></w:pPr><w:r><w:rPr><w:color w:val=\"FF0000\"/></w:rPr><w:t>";

  private static final String TABLE_PROPORTION_COLUMN_4 = "%</w:t></w:r></w:p></w:tc></w:tr>";

  private static final String TABLE_END = "</w:tbl>";

  @Override
  public String getXmlKeywordTabelleLiefertermine() {
    return XML_KEYWORD_TABELLELIEFERTERMINE;
  }

  @Override
  public String getXmlKeywordVerrechnungstagessatz() {
    return XML_KEYWORD_VERRECHNUNGSTAGESSATZ;
  }

  @Override
  public String getXmlKeywordTabelleAnteile() {
    return XML_KEYWORD_TABELLEANTEILE;
  }

  @Override
  public String getXmlKeywordGesamtvolumenAlsZahl() {
    return XML_KEYWORD_GESAMTVOLUMENALSZAHL;
  }

  @Override
  public String getXmlKeywordGesamtvolumenAlsWort() {
    return XML_KEYWORD_GESAMTVOLUMENALSWORT;
  }

  @Override
  public String getXmlKeywordProjekt() {
    return XML_KEYWORD_PROJEKT;
  }

  @Override
  public String getXmlKeywordTitel() {
    return XML_KEYWORD_TITEL;
  }

  @Override
  public String getXmlKeywordProjectForgeID() {
    return XML_KEYWORD_PROJECTFOGEID;
  }

  @Override
  public String getXmlKeywordVersion() {
    return XML_KEYWORD_VERSION;
  }

  @Override
  public String getXmlKeywordAngebotserstellungsdatum() {
    return XML_KEYWORD_ANGEBOTSERSTELLUNGSDATUM;
  }

  @Override
  public String getXmlKeywordFinaleleistungserbringungsdatum() {
    return XML_KEYWORD_FINALELEISTUNGSERBRINGUNGSDATUM;
  }

  @Override
  public String getXmlKeywordTabelleLeistungserbringung() {
    return XML_KEYWORD_TABELLELEISTUNGSERBRINGUNG;
  }

  @Override
  public String getXmlKeywordLeistungsdauerintagen() {
    return XML_KEYWORD_LEISTUNGSDAUERINTAGEN;
  }

  @Override
  public String getXmlKeywordLeistungsdauerinstunden() {
    return XML_KEYWORD_LEISTUNGSDAUERINSTUNDEN;
  }

  @Override
  public String getXmlKeywordAnsprechpatner() {
    return XML_KEYWORD_ANSPRECHPATNER;
  }

  @Override
  public String getXmlKeywordTabelleProjektmitarbeiter() {
    return XML_KEYWORD_TABELLEPROJEKTMITARBEITER;
  }

  @Override
  public String getXmlKeywordStundensatz() {
    return XML_KEYWORD_STUNDENSATZ;
  }

  @Override
  public String getXmlKeywordTagessatz() {
    return XML_KEYWORD_TAGESSATZ;
  }

  @Override
  public String getXmlKeywordBindungsfristdatum() {
    return XML_KEYWORD_BINDUNGSFRISTDATUM;
  }

  @Override
  public String getXmlKeywordProjektnfa() {
    return XML_KEYWORD_PROJEKTNFA;
  }

  @Override
  public String getXmlKeywordProjektArt() {
    return XML_KEYWORD_VERTRAGSART;
  }

  @Override
  public String getPathFolderToUnzip() {
    return PATH_FOLDER_TO_UNZIP;
  }

  @Override
  public String getPathWordDocumentXml() {
    return PATH_WORD_DOCUMENT_XML;
  }

  public String getPathWordFooterXml() {
    return PATH_WORD_FOOTER_XML;
  }

  @Override
  public String getTableDateHeader() {
    return TABLE_DATE_HEADER;
  }

  @Override
  public String getTableDateColumn1() {
    return TABLE_DATE_COLUMN_1;
  }

  @Override
  public String getTableDateColumn2() {
    return TABLE_DATE_COLUMN_2;
  }

  @Override
  public String getTableDateColumn3() {
    return TABLE_DATE_COLUMN_3;
  }

  @Override
  public String getTableDateColumn4() {
    return TABLE_DATE_COLUMN_4;
  }

  @Override
  public String getTableProportionHeader() {
    return TABLE_PROPORTION_HEADER;
  }

  @Override
  public String getTableProportionColumn1() {
    return TABLE_PROPORTION_COLUMN_1;
  }

  @Override
  public String getTableProportionColumn2() {
    return TABLE_PROPORTION_COLUMN_2;
  }

  @Override
  public String getTableProportionColumn3() {
    return TABLE_PROPORTION_COLUMN_3;
  }

  @Override
  public String getTableProportionColumn4() {
    return TABLE_PROPORTION_COLUMN_4;
  }

  @Override
  public String getTableLeistungsumfangHeader() {
    return TABLE_LEISTUNGSUMFANG_HEADER;
  }

  @Override
  public String getTableProjektmitarbeiterHeader() {
    return TABLE_PROJEKTMITARBEITER_HEADER;
  }

  @Override
  public String getTableLeistungsumfangColumn1() {
    return TABLE_LEISTUNGSUMFANG_COLUMN_1;
  }

  @Override
  public String getTableLeistungsumfangColumn2() {
    return TABLE_LEISTUNGSUMFANG_COLUMN_2;
  }

  @Override
  public String getTableLeistungsumfangColumn3() {
    return TABLE_LEISTUNGSUMFANG_COLUMN_3;
  }

  @Override
  public String getTableProjektmitarbeiterColumn1() {
    return TABLE_PROJEKTMITARBEITER_COLUMN_1;
  }

  @Override
  public String getTableProjektmitarbeiterColumn2() {
    return TABLE_PROJEKTMITARBEITER_COLUMN_2;
  }

  @Override
  public String getTableProjektmitarbeiterColumn3() {
    return TABLE_PROJEKTMITARBEITER_COLUMN_3;
  }

  @Override
  public String getTableProjektmitarbeiterColumn4() {
    return TABLE_PROJEKTMITARBEITER_COLUMN_4;
  }

  @Override
  public String getTableEnd() {
    return TABLE_END;
  }

  @Override
  public String getGitLoginUsername() {
    return GIT_LOGIN_USERNAME;
  }

  @Override
  public String getGitLoginPasswort() {
    return GIT_LOGIN_PASSWORD;
  }

  @Override
  public String getGitPrivSshId() {
    return GIT_PRIV_SSH_ID_DOCUMENTGENERATOR;
  }

  @Override
  public String getGitPubSshId() {
    return GIT_PUB_SSH_ID_DOCUMENTGENERATOR;
  }

  @Override
  public String getGitRepositoryUrl() {
    return GIT_URL_OF_REPO;
  }

  @Override
  public String getRemoteName() {
    return GIT_REMOTE;
  }

  @Override
  public String getTagName() {
    return GIT_TAG;
  }

  @Override
  public File getFileRepository() {
    return GIT_FILE_REPOSITORY;
  }

  @Override
  public int getGitPort() {
    return GIT_PORT;
  }

  @Override
  public String getFileRepositoryPath() {
    return GIT_FILE_REPOSITORY_PATH;
  }

  @Override
  public String getDocumentTypeAngebot() {
    return DocumentType.ANGEBOT.toString();
  }

  @Override
  public String getDocumentTypeEinzelvertragFestpreis() {
    return DocumentType.EINZELVERTRAG_FESTPREIS.toString();
  }

  @Override
  public String getDocumentTypeEinzelvertragDienstleistung() {
    return DocumentType.EINZELVERTRAG_DIENSTLEISTUNG.toString();
  }

  @Override
  public String getPathOfTemplates() {
    return TEMPLATE;
  }

  @Override
  public String getPathOfAttachements() {
    return ATTACHMENTS;
  }

  @Override
  public String getPathOfExportFile() {
    return EXPORTFILE;
  }

  @Override
  public String getNameOfExportFile() {
    return EXPORTFILENAME;
  }

  @Override
  public String getPathOfStorageDirectory() {
    return PATH_TEMP;
  }

  @Override
  public String getFileExtensionTypeTo() {
    return FILE_TYPE_TO;
  }

  @Override
  public String getFileExtensionTypeFrom() {
    return FILE_TYPE_FROM;
  }

  @Override
  public String getAngebotArtFestpreis() {
    return ANGEBOT_ART_FESTPREIS_TEXT;
  }

  @Override
  public String getAngebotArtDienstleistung() {
    return ANGEBOT_ART_DIENSTLEISTUNG_TEXT;
  }
}
