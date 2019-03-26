package de.micromata.dokumentor.text;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import de.micromata.dokumentor.Creator;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TextCreator implements Creator {

  private final Charset charset = StandardCharsets.UTF_8;

  @Override
  public byte[] create(HTTPGatewayContext context) {
    URI source =
        URI.create(context.getQueryParameters().get("source").orElseThrow(RuntimeException::new));

    System.out.println("source = " + source);
    Path path;
    try {
      path = Path.of(source);
    } catch (IllegalArgumentException e) {
      URL url = TextCreator.class.getResource(source.toString());
      if (url == null) {
        throw new RuntimeException("source not found");
      }
      try {
        path = Path.of(url.toURI());
      } catch (URISyntaxException ex) {
        throw new RuntimeException("", ex);
      }
    }

    Map<String, String> mapping = new HashMap<>();
    for (String line : context.getQueryParameters().getValues("mapping")) {
      String[] split = line.split(":");
      mapping.put(split[0], split[1]);
    }

    System.out.println("mapping = " + mapping);

    try {
      String content = Files.readString(path, charset);
      for (Map.Entry<String, String> entry : mapping.entrySet()) {
        content = content.replace(entry.getKey(), entry.getValue());
      }

      context.setResponseHeader(
          "Content-Type", "text/plain;charset=" + charset.name().toLowerCase());
      return content.getBytes(charset);
    } catch (IOException e) {
      throw new RuntimeException("" + e.getMessage(), e);
    }
  }
}
