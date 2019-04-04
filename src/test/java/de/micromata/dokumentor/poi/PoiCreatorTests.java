package de.micromata.dokumentor.poi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

class PoiCreatorTests {

  @Test
  void helloWorld() throws Exception {
    var creator = new PoiCreator();
    try (var stream = new FileInputStream("src/test/resources/hello.docx")) {
      var document = new XWPFDocument(stream);
      var before = new XWPFWordExtractor(document).getText().strip();
      assertEquals("Hello #Variable", before);
      creator.replacePOI(document, "#Variable", "World");
      var actual = new XWPFWordExtractor(document).getText().strip();
      assertEquals("Hello World", actual);
    }
  }
}
