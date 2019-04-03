package de.micromata.dokumentor.text;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import org.junit.Test;

public class TextCreatorTests {

  @Test
  public void creatorTextUsingPostMethod() {
    var creator = new TextCreator();
    var actual = creator.readContent(URI.create("does-not-exists"));
    assertTrue(actual.contains("Resource not found!"));
    assertTrue(actual.contains("uri: does-not-exists"));
  }
}
