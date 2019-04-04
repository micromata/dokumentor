package de.micromata.dokumentor.poi;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import de.micromata.dokumentor.Creator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class PoiCreator implements Creator {

  private final Charset charset = StandardCharsets.UTF_8;

  byte[] readAllBytes(URI uri) {
    try {
      if (!uri.isAbsolute()) {
        var resource = PoiCreator.class.getResource("/" + uri.toString());
        if (resource == null) {
          var message = new StringBuilder();
          message.append("Resource not found!\n");
          message.append("\n");
          message.append(" uri: ").append(uri).append("\n");
          message.append("\n");
          message.append("Files\n\n");
          var files = Path.of(".").toFile().listFiles();
          if (files != null) {
            Arrays.stream(files).forEach(f -> message.append(f).append("\n"));
          }
          message.append("\n");
          message.append("System properties\n\n");
          System.getProperties()
              .forEach((key, value) -> message.append(key).append("=").append(value).append("\n"));
          throw new IllegalArgumentException(message.toString());
        }
        var path = Path.of(resource.toURI());
        return Files.readAllBytes(path);
      }
      var bytes = new ByteArrayOutputStream();
      try (var stream = uri.toURL().openStream()) {
        stream.transferTo(bytes);
      }
      return bytes.toByteArray();
    } catch (Exception e) {
      throw new IllegalArgumentException("Error reading content from " + uri + " : " + e, e);
    }
  }

  @Override
  public byte[] create(HTTPGatewayContext context) {
    var source = context.getQueryParameters().get("source").orElse(null);
    if (source == null) {
      return error(context, "No source argument supplied!");
    }

    try {
      var bytes = readAllBytes(URI.create(source));
      var input = new ByteArrayInputStream(bytes);
      var document = new XWPFDocument(input);

      var mapping = new HashMap<String, String>();
      for (var line : context.getQueryParameters().getValues("mapping")) {
        var split = line.split(":");
        mapping.put(split[0], split[1]);
      }
      for (var entry : mapping.entrySet()) {
        replacePOI(document, entry.getKey(), entry.getValue());
      }
      var result = new ByteArrayOutputStream();
      document.write(result);

      context.setResponseHeader("Content-Type", "application/octet-stream");
      return result.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
      return error(context, "Error handling " + source + ": " + e);
    }
  }

  private byte[] error(HTTPGatewayContext context, String message) {
    context.setResponseHeader("Content-Type", "text/plain;charset=" + charset.name().toLowerCase());
    return message.getBytes(charset);
  }

  void replacePOI(XWPFDocument doc, String placeHolder, String replaceText) {
    // REPLACE ALL HEADERS
    for (XWPFHeader header : doc.getHeaderList()) {
      replaceAllBodyElements(header.getBodyElements(), placeHolder, replaceText);
    }
    // REPLACE BODY
    replaceAllBodyElements(doc.getBodyElements(), placeHolder, replaceText);
  }

  private void replaceAllBodyElements(
      List<IBodyElement> bodyElements, String placeHolder, String replaceText) {
    for (IBodyElement bodyElement : bodyElements) {
      if (bodyElement.getElementType().compareTo(BodyElementType.PARAGRAPH) == 0)
        replaceParagraph((XWPFParagraph) bodyElement, placeHolder, replaceText);
      if (bodyElement.getElementType().compareTo(BodyElementType.TABLE) == 0)
        replaceTable((XWPFTable) bodyElement, placeHolder, replaceText);
    }
  }

  private void replaceTable(XWPFTable table, String placeHolder, String replaceText) {
    for (XWPFTableRow row : table.getRows()) {
      for (XWPFTableCell cell : row.getTableCells()) {
        for (IBodyElement bodyElement : cell.getBodyElements()) {
          if (bodyElement.getElementType().compareTo(BodyElementType.PARAGRAPH) == 0) {
            replaceParagraph((XWPFParagraph) bodyElement, placeHolder, replaceText);
          }
          if (bodyElement.getElementType().compareTo(BodyElementType.TABLE) == 0) {
            replaceTable((XWPFTable) bodyElement, placeHolder, replaceText);
          }
        }
      }
    }
  }

  private void replaceParagraph(XWPFParagraph paragraph, String placeHolder, String replaceText) {
    for (XWPFRun r : paragraph.getRuns()) {
      String text = r.getText(r.getTextPosition());
      if (text != null && text.contains(placeHolder)) {
        text = text.replace(placeHolder, replaceText);
        r.setText(text, 0);
      }
    }
  }
}
