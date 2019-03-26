package de.micromata.dokumentor;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import de.micromata.dokumentor.text.TextCreator;

public class Dokumentor implements Creator {

  @Override
  public byte[] create(HTTPGatewayContext context) {
    String key = context.getQueryParameters().get("creator").orElse("qr");
    Creator creator = createCreator(context, key);
    return creator.create(context);
  }

  private Creator createCreator(HTTPGatewayContext context, String key) {
    switch (key.toLowerCase()) {
      case "qr":
        return new QRGen(context.getInvocationContext().getRuntimeContext());
      case "txt":
      case "xml":
        return new TextCreator();
      case "doc":
        throw new UnsupportedOperationException("Doc files aren't supported, yet");
      default:
        throw new RuntimeException("Unknown transformer key: " + key);
    }
  }
}
