package de.micromata.dokumentor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fnproject.fn.testing.FnTestingRule;
import org.junit.Rule;
import org.junit.Test;

public class DokumentorTests {

  @Rule
  public FnTestingRule fn =
      FnTestingRule.createDefault().addSharedClassPrefix("org.w3c").addSharedClassPrefix("org.xml");

  @Test
  public void txtHelloWorld() {
    fn.givenEvent()
        .withHeader(
            "Fn-Http-Request-Url",
            "http://www.example.com/dokumentor?creator=txt&source=hello2.txt&mapping=VARIABLE:earth&mapping=PLANET2:mars")
        .withHeader("Fn-Http-Method", "GET")
        .enqueue();
    fn.thenRun(Dokumentor.class, "create");

    String[] actual = new String(fn.getOnlyResult().getBodyAsBytes()).split("\\R");
    assertEquals("hello earth", actual[0]);
    assertEquals("hello mars", actual[1]);
  }

  @Test
  public void txtApacheLicense() {
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
