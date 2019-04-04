package de.micromata.dokumentor.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import org.junit.jupiter.api.Test;

class TextCreatorTests {

  @Test
  void creatorTextUsingPostMethod() {
    var creator = new TextCreator();
    var actual = creator.readContent(URI.create("does-not-exists"));
    assertTrue(actual.contains("Resource not found!"));
    assertTrue(actual.contains("uri: does-not-exists"));
  }
}
