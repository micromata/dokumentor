package de.micromata.dokumentor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fnproject.fn.testing.FnTestingRule;
import org.junit.Rule;
import org.junit.Test;

public class DokumentorTests {

  @Rule public FnTestingRule fn = FnTestingRule.createDefault();

  @Test
  public void creatorTextUsingPostMethod() {
    fn.givenEvent()
        .withHeader(
            "Fn-Http-Request-Url",
            "http://www.example.com/dokumentor?creator=txt&source=hello.txt&mapping=VARIABLE:world")
        .withHeader("Fn-Http-Method", "GET")
        .enqueue();
    fn.thenRun(Dokumentor.class, "create");

    assertEquals("hello world", new String(fn.getOnlyResult().getBodyAsBytes()));
  }

  @Test
  public void creatorTextUsingExternalResource() {
    fn.givenEvent()
        .withHeader(
            "Fn-Http-Request-Url",
            "http://www.example.com/dokumentor?creator=txt&source=https%3A%2F%2Fwww.apache.org%2Flicenses%2FLICENSE-2.0.txt&mapping=Apache:Comanche")
        .withHeader("Fn-Http-Method", "GET")
        .enqueue();
    fn.thenRun(Dokumentor.class, "create");

    assertTrue(new String(fn.getOnlyResult().getBodyAsBytes()).contains("Comanche License"));
  }
}
