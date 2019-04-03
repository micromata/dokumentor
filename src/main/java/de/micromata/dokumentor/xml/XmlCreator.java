package de.micromata.dokumentor.xml;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import de.micromata.dokumentor.Creator;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class XmlCreator implements Creator {

  private final Charset charset = StandardCharsets.UTF_8;

  // Returns Path as String
  private String getPath(URI uri) {
    try {
      if (!uri.isAbsolute()) {
        var resource = XmlCreator.class.getResource("/" + uri.toString());
        if (resource == null) {
          return "Resource " + uri + " not found!";
        }
        var path = Path.of(resource.toURI());
        return path.toString();
      }
      return "Absolut Uri are not supported!";
    } catch (Exception e) {
      return "Error reading content from " + uri + " : " + e;
    }
  }

  @Override
  public byte[] create(HTTPGatewayContext context) {
    try {
      var source = context.getQueryParameters().get("source").orElse(null);
      var path = source == null ? "No source argument supplied!" : getPath(URI.create(source));

      var mapping = new HashMap<String, String>();
      for (var line : context.getQueryParameters().getValues("mapping")) {
        var split = line.split(":");
        mapping.put(split[0], split[1]);
      }

      // Todo: Mirco Nuhn - XmlEditingservice implementieren (y) mapping übergeben

      context.setResponseHeader("Content-Type", "application/octet-stream");
      // Todo: Mirco Nuhn -  return ergebnis ( path -> to return )
      return Files.readAllBytes(Path.of(path));
    } catch (Exception e) {
      context.setResponseHeader(
          "Content-Type", "text/plain;charset=" + charset.name().toLowerCase());
      return ("XML Creator failed: " + e).getBytes(charset);
    }
  }
}
