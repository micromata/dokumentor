package de.micromata.dokumentor.xml.classpackage.config;

/** Created by Mirco Nuhn - Micromata Gmbh */
public interface XmlEditingServiceConfig {

  String getPathFolderToUnzip();

  String getPathWordDocumentXml();

  String getPathWordFooterXml();

  String getXmlKeywordTitel();

  String getXmlKeywordTabelleLiefertermine();

  String getXmlKeywordVerrechnungstagessatz();

  String getXmlKeywordTabelleAnteile();

  String getXmlKeywordGesamtvolumenAlsZahl();

  String getXmlKeywordGesamtvolumenAlsWort();

  String getXmlKeywordProjekt();

  String getXmlKeywordProjectForgeID();

  String getXmlKeywordVersion();

  String getXmlKeywordAngebotserstellungsdatum();

  String getXmlKeywordFinaleleistungserbringungsdatum();

  String getXmlKeywordTabelleLeistungserbringung();

  String getXmlKeywordLeistungsdauerintagen();

  String getXmlKeywordLeistungsdauerinstunden();

  String getXmlKeywordAnsprechpatner();

  String getXmlKeywordTabelleProjektmitarbeiter();

  String getXmlKeywordStundensatz();

  String getXmlKeywordTagessatz();

  String getXmlKeywordBindungsfristdatum();

  String getXmlKeywordProjektnfa();

  String getTableDateHeader();

  String getTableDateColumn1();

  String getTableDateColumn2();

  String getTableDateColumn3();

  String getTableDateColumn4();

  String getTableProportionHeader();

  String getTableProportionColumn1();

  String getTableProportionColumn2();

  String getTableProportionColumn3();

  String getTableProportionColumn4();

  String getTableLeistungsumfangHeader();

  String getTableLeistungsumfangColumn1();

  String getTableLeistungsumfangColumn2();

  String getTableLeistungsumfangColumn3();

  String getTableProjektmitarbeiterHeader();

  String getTableProjektmitarbeiterColumn1();

  String getTableProjektmitarbeiterColumn2();

  String getTableProjektmitarbeiterColumn3();

  String getTableProjektmitarbeiterColumn4();

  String getTableEnd();

  String getDocumentTypeEinzelvertragFestpreis();

  String getDocumentTypeEinzelvertragDienstleistung();

  String getAngebotArtFestpreis();

  String getAngebotArtDienstleistung();

  String getXmlKeywordProjektArt();
}
