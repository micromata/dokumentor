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

  private String readContent(URI uri) {
    try {
      if (!uri.isAbsolute()) {
        var resource = XmlCreator.class.getResource("/" + uri.toString());
        if (resource == null) {
          return "Resource " + uri + " not found!";
        }
        var path = Path.of(resource.toURI());
        return Files.readString(path, charset);
      }
      try (var stream = uri.toURL().openStream()) {
        return new String(stream.readAllBytes(), charset);
      }
    } catch (Exception e) {
      return "Error reading content from " + uri + " : " + e;
    }
  }

  @Override
  public byte[] create(HTTPGatewayContext context) {
    var source = context.getQueryParameters().get("source").orElse(null);
    var content = source == null ? "No source argument supplied!" : readContent(URI.create(source));

    var mapping = new HashMap<String, String>();
    for (var line : context.getQueryParameters().getValues("mapping")) {
      var split = line.split(":");
      mapping.put(split[0], split[1]);
    }

    for (var entry : mapping.entrySet()) {
      content = content.replace(entry.getKey(), entry.getValue());
    }

    context.setResponseHeader("Content-Type", "text/plain;charset=" + charset.name().toLowerCase());
    return content.getBytes(charset);
  }
}