package de.micromata.dokumentor.xml.classpackage.technically;

import de.micromata.dokumentor.xml.classpackage.config.XmlEditingServiceConfig;
import de.micromata.dokumentor.xml.classpackage.domain.Angebot;
import de.micromata.dokumentor.xml.classpackage.domain.Einzelvertrag;
import de.micromata.dokumentor.xml.classpackage.domain.NumberAsWord;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/** Created by Mirco Nuhn - Micromata Gmbh */
public class XmlEditingService {

  private final XmlEditingServiceConfig xmleditingserviceconfig;

  private String tableDate;
  private String tableProportion;
  private String tableLeistungsumfang;
  private String tableProjektmitarbeiter;

  public void editTestDokument(HashMap<String,String> mapping) throws IOException {
    List<String> fileNamesList = new ArrayList();
    Files.newDirectoryStream(
            Paths.get(xmleditingserviceconfig.getPathFolderToUnzip()),
            path -> path.toString().endsWith(".xml"))
            .forEach(filePath -> fileNamesList.add(filePath.toString()));

    String xmlFilePath = xmleditingserviceconfig.getPathWordDocumentXml();
    System.out.println(xmlFilePath);
    String xmlString = getXML(xmlFilePath);
    xmlString = replaceTestDokument(xmlString, mapping);
    safeToXMLFile(xmlString, xmlFilePath);
  }

  public String replaceTestDokument(String xmlAsString, HashMap mapping){

    System.out.println("ANFANG ----------------------");

    System.out.println(mapping);

    System.out.println("ENDE ------------------------");

    return "";
  }

  /** */
  public void editEinzelvertrag(Einzelvertrag ev, NumberAsWord naw) throws IOException {

    List<String> fileNamesList = new ArrayList();
    Files.newDirectoryStream(
            Paths.get(xmleditingserviceconfig.getPathFolderToUnzip()),
            path -> path.toString().endsWith(".xml"))
        .forEach(filePath -> fileNamesList.add(filePath.toString()));

    String xmlFilePath = xmleditingserviceconfig.getPathWordDocumentXml();
    String xmlString = getXML(xmlFilePath);
    buildEinzelvertragTables(ev.getStrArrTableInformation());
    xmlString =
        replaceEinzelvertragKeywords(xmlString, ev, naw.intToText(ev.getGesamtvolumenAlsZahl()));
    safeToXMLFile(xmlString, xmlFilePath);
  }

  /** */
  public void editAngebot(Angebot ag, NumberAsWord naw) throws IOException {

    List<String> fileNamesList = new ArrayList();
    Files.newDirectoryStream(
            Paths.get(xmleditingserviceconfig.getPathFolderToUnzip()),
            path -> path.toString().endsWith(".xml"))
        .forEach(filePath -> fileNamesList.add(filePath.toString()));

    // Replace in Document-Body
    String xmlFilePathForDocumentXML = xmleditingserviceconfig.getPathWordDocumentXml();
    String xmlStringForDocumentXML = getXML(xmlFilePathForDocumentXML);
    buildAngebotTables(ag.getStrArrLeistungsUmfang(), ag.getStrArrProjektmitarbeiter());
    xmlStringForDocumentXML = replaceAngebotKeywords(xmlStringForDocumentXML, ag);
    safeToXMLFile(xmlStringForDocumentXML, xmlFilePathForDocumentXML);

    // Replace in Footer
    String xmlFilePathForFooterXML = xmleditingserviceconfig.getPathWordFooterXml();
    String xmlStringForFooterXML = getXML(xmlFilePathForFooterXML);
    xmlStringForFooterXML = replaceAngebotKeywords(xmlStringForFooterXML, ag);
    safeToXMLFile(xmlStringForFooterXML, xmlFilePathForFooterXML);
  }

  /** */
  private String getXML(String pathToXml) {
    String xmlString = "";
    try {
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      InputSource is = new InputSource(pathToXml);
      Document document = docBuilderFactory.newDocumentBuilder().parse(is);
      StringWriter sw = new StringWriter();
      Transformer serializer = TransformerFactory.newInstance().newTransformer();
      serializer.transform(new DOMSource(document), new StreamResult(sw));
      xmlString = sw.toString();
      return xmlString;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /** */
  private String replaceEinzelvertragKeywords(
      String xmlAsString, Einzelvertrag ev, String fixedpriceAsWord) {

    xmlAsString =
        xmlAsString.replaceAll(xmleditingserviceconfig.getXmlKeywordTitel(), ev.getTitel());
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordTabelleLiefertermine(), tableDate);
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordGesamtvolumenAlsZahl(),
            numbersValidatorWithFloat(ev.getGesamtvolumenAlsZahl()));
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordGesamtvolumenAlsWort(), fixedpriceAsWord);
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordVerrechnungstagessatz(),
            numbersValidatorWithString(ev.getVerrechnungstagessatz()));
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordTabelleAnteile(), tableProportion);

    return xmlAsString;
  }

  /** */
  private String replaceAngebotKeywords(String xmlAsString, Angebot ag) {

    xmlAsString =
        xmlAsString.replaceAll(xmleditingserviceconfig.getXmlKeywordTitel(), ag.getTitel());
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordLeistungsdauerinstunden(),
            leistungsdauerValidatorWithFloat(ag.getLeistungsDauerInStunden()));
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordProjectForgeID(), ag.getProjektForgeID());
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordGesamtvolumenAlsZahl(),
            numbersValidatorWithFloat(ag.getGesamtvolumenAlsZahl()));
    xmlAsString =
        xmlAsString.replaceAll(xmleditingserviceconfig.getXmlKeywordVersion(), ag.getVersion());
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordAngebotserstellungsdatum(),
            ag.getAngebotsErstellungsDatum());
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordFinaleleistungserbringungsdatum(),
            ag.getFinaleLeistungserbringungDatum());
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordAnsprechpatner(), ag.getAnsprechpatner());
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordLeistungsdauerintagen(),
            leistungsdauerValidatorWithFloat(ag.getLeistungsDauerInTagen()));
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordTagessatz(),
            numbersValidatorWithFloat(ag.getTagessatz()));
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordStundensatz(),
            numbersValidatorWithFloat(ag.getStundensatz()));
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordBindungsfristdatum(), ag.getBindungsfristDatum());
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordProjektnfa(), ag.getProjektnfa());
    xmlAsString =
        xmlAsString.replaceAll(xmleditingserviceconfig.getXmlKeywordProjekt(), ag.getProjekt());
    if (ag.getArt()
        .equalsIgnoreCase(xmleditingserviceconfig.getDocumentTypeEinzelvertragDienstleistung())) {
      xmlAsString =
          xmlAsString.replaceAll(
              xmleditingserviceconfig.getXmlKeywordProjektArt(),
              xmleditingserviceconfig.getAngebotArtDienstleistung());
    } else if (ag.getArt()
        .equalsIgnoreCase(xmleditingserviceconfig.getDocumentTypeEinzelvertragFestpreis())) {
      xmlAsString =
          xmlAsString.replaceAll(
              xmleditingserviceconfig.getXmlKeywordProjektArt(),
              xmleditingserviceconfig.getAngebotArtFestpreis());
    }

    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordTabelleLeistungserbringung(),
            tableLeistungsumfang);
    xmlAsString =
        xmlAsString.replaceAll(
            xmleditingserviceconfig.getXmlKeywordTabelleProjektmitarbeiter(),
            tableProjektmitarbeiter);

    return xmlAsString;
  }

  /** */
  private String formatNumber(float fixedprice) {

    NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
    DecimalFormat df = (DecimalFormat) nf;

    return df.format(fixedprice);
  }

  /** */
  private String numbersValidatorWithFloat(float number) {
    String numberString = Float.toString(number);
    return numbersValidatorWithString(numberString);
  }

  /** */
  private String numbersValidatorWithString(String numberString) {

    if (numberString.matches("^[0-9]*$")) {
      numberString = numberString + ".00";
    } else {
      if (numberString.matches("^[0-9]*[.][0-9]$")) {
        numberString = numberString + "0";
      }
    }

    numberString = numberString.replaceAll("\\.", "\\,");

    return numberString;
  }

  /** */
  private String leistungsdauerValidatorWithFloat(float number) {
    String numberString = Float.toString(number);
    return leistungsdauerValidatorWithString(numberString);
  }

  /** */
  private String leistungsdauerValidatorWithString(String numberString) {

    if (numberString.matches("^[0-9]*[.][0]$")) {
      numberString = numberString.replaceAll("\\.0", "");
    } else {
      numberString = numberString.replaceAll("\\.", "\\,");
    }

    return numberString;
  }

  /** */
  public XmlEditingService(final XmlEditingServiceConfig xmleditingserviceconfig) {
    this.xmleditingserviceconfig = xmleditingserviceconfig;
  }

  /** */
  private void buildEinzelvertragTables(String[][] strArr) {

    tableDate = xmleditingserviceconfig.getTableDateHeader();
    tableProportion = xmleditingserviceconfig.getTableProportionHeader();

    for (int x = 0; x < strArr.length; x++) {
      tableDate =
          tableDate
              + xmleditingserviceconfig.getTableDateColumn1()
              + (x + 1)
              + xmleditingserviceconfig.getTableDateColumn2()
              + strArr[x][0]
              + xmleditingserviceconfig.getTableDateColumn3()
              + strArr[x][1]
              + xmleditingserviceconfig.getTableDateColumn4();
      tableProportion =
          tableProportion
              + xmleditingserviceconfig.getTableProportionColumn1()
              + (x + 1)
              + xmleditingserviceconfig.getTableProportionColumn2()
              + strArr[x][0]
              + xmleditingserviceconfig.getTableProportionColumn3()
              + strArr[x][2]
              + xmleditingserviceconfig.getTableProportionColumn4();
    }

    tableDate = tableDate + xmleditingserviceconfig.getTableEnd();
    tableProportion = tableProportion + xmleditingserviceconfig.getTableEnd();
  }

  /** */
  private void buildAngebotTables(
      String[][] strArrLeistungsumfang, String[][] strArrProjektmitarbeiter) {

    tableLeistungsumfang = xmleditingserviceconfig.getTableLeistungsumfangHeader();
    tableProjektmitarbeiter = xmleditingserviceconfig.getTableProjektmitarbeiterHeader();

    for (int x = 0; x < strArrProjektmitarbeiter.length; x++) {
      tableProjektmitarbeiter =
          tableProjektmitarbeiter
              + xmleditingserviceconfig.getTableProjektmitarbeiterColumn1()
              + strArrProjektmitarbeiter[x][0]
              + xmleditingserviceconfig.getTableProjektmitarbeiterColumn2()
              + leistungsdauerValidatorWithString(strArrProjektmitarbeiter[x][1])
              + xmleditingserviceconfig.getTableProjektmitarbeiterColumn3()
              + leistungsdauerValidatorWithString(strArrProjektmitarbeiter[x][2])
              + xmleditingserviceconfig.getTableProjektmitarbeiterColumn4();
    }

    for (int x = 0; x < strArrLeistungsumfang.length; x++) {
      tableLeistungsumfang =
          tableLeistungsumfang
              + xmleditingserviceconfig.getTableLeistungsumfangColumn1()
              + strArrLeistungsumfang[x][0]
              + xmleditingserviceconfig.getTableLeistungsumfangColumn2()
              + leistungsdauerValidatorWithString(strArrLeistungsumfang[x][1])
              + xmleditingserviceconfig.getTableLeistungsumfangColumn3();
    }

    tableLeistungsumfang = tableLeistungsumfang + xmleditingserviceconfig.getTableEnd();
    tableProjektmitarbeiter = tableProjektmitarbeiter + xmleditingserviceconfig.getTableEnd();
  }

  /** */
  public void safeToXMLFile(String xmlString, String path) {

    try {
      java.io.FileWriter fw = new FileWriter(path);
      fw.write(xmlString);
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
