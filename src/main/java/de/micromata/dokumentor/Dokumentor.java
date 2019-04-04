package de.micromata.dokumentor;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import de.micromata.dokumentor.poi.PoiCreator;
import de.micromata.dokumentor.text.TextCreator;
import de.micromata.dokumentor.xml.XmlCreator;

public class Dokumentor implements Creator {

  @Override
  public byte[] create(HTTPGatewayContext context) {
    String key = context.getQueryParameters().get("creator").orElse("txt");
    Creator creator = createCreator(key);
    return creator.create(context);
  }

  private Creator createCreator(String key) {
    switch (key.toLowerCase()) {
      case "poi":
        return new PoiCreator();
      case "txt":
        return new TextCreator();
      case "xml":
        return new XmlCreator();
      default:
        throw new RuntimeException("Unknown transformer key: " + key);
    }
  }
}
