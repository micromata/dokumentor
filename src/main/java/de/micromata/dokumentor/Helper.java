package de.micromata.dokumentor;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Optional;

public class Helper {

  public static Optional<Path> findJarPath(Class<?> context) {
    if (context == null) context = Helper.class;
    String rawName = context.getName();
    String classFileName;
    /* rawName is something like package.name.ContainingClass$ClassName. We need to turn this into ContainingClass$ClassName.class. */ {
      int idx = rawName.lastIndexOf('.');
      classFileName = (idx == -1 ? rawName : rawName.substring(idx + 1)) + ".class";
    }

    String uri = context.getResource(classFileName).toString();
    if (uri.startsWith("file:")) {
      return Optional.empty();
      // throw new IllegalStateException(
      //    "This class has been loaded from a directory and not from a jar file.");
    }
    if (!uri.startsWith("jar:file:")) {
      return Optional.empty();
      // int idx = uri.indexOf(':');
      // String protocol = idx == -1 ? "(unknown)" : uri.substring(0, idx);
      // throw new IllegalStateException(
      //      "This class has been loaded remotely via the "
      //          + protocol
      //          + " protocol. Only loading from a jar on the local file system is supported.");
    }

    int idx = uri.indexOf('!');
    // As far as I know, the if statement below can't ever trigger, so it's more of a sanity check
    // thing.
    if (idx == -1) {
      return Optional.empty();
      // throw new IllegalStateException(
      //    "You appear to have loaded this class from a local jar file, but I can't make sense of
      // the URL!");
    }

    try {
      String fileName =
          URLDecoder.decode(
              uri.substring("jar:file:".length(), idx), Charset.defaultCharset().name());
      return Optional.of(Path.of(fileName).toAbsolutePath());
    } catch (Exception e) {
      throw new InternalError("default charset doesn't exist. Your VM is borked.");
    }
  }
}
