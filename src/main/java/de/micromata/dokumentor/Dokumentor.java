package de.micromata.dokumentor;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import de.micromata.dokumentor.poi.PoiCreator;
import de.micromata.dokumentor.text.TextCreator;
import de.micromata.dokumentor.xml.XmlCreator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Dokumentor implements Creator {

  public static void main(String[] args) throws Exception {
    System.out.println("Dokumentor");
    System.out.println(" args: " + List.of(args));
    if (args.length == 1) {
      var path = Path.of(args[0]).toAbsolutePath().normalize();
      Files.createTempFile(path.getParent(), "generated-", ".txt");
    }
  }

  @Override
  public byte[] create(HTTPGatewayContext context) {
    var key = context.getQueryParameters().get("creator").orElse("txt");
    var creator = createCreator(key);
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
