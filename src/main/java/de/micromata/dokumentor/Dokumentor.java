package de.micromata.dokumentor;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import de.micromata.dokumentor.text.TextCreator;
import de.micromata.dokumentor.xml.XmlCreator;

public class Dokumentor implements Creator {

  @Override
  public byte[] create(HTTPGatewayContext context) {
    String key = context.getQueryParameters().get("creator").orElse("qr");
    Creator creator = createCreator(context, key);
    return creator.create(context);
  }

  private Creator createCreator(HTTPGatewayContext context, String key) {
    switch (key.toLowerCase()) {
      case "txt":
        return new TextCreator();
      case "xml":
        return new XmlCreator();
      case "doc":
        throw new UnsupportedOperationException("Doc files aren't supported, yet");
      default:
        throw new RuntimeException("Unknown transformer key: " + key);
    }
  }
}
