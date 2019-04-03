package de.micromata.dokumentor.text;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import de.micromata.dokumentor.Creator;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

public class TextCreator implements Creator {

  private final Charset charset = StandardCharsets.UTF_8;

  String readContent(URI uri) {
    try {
      if (!uri.isAbsolute()) {
        var resource = TextCreator.class.getResource("/" + uri.toString());
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
          return message.toString();
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
