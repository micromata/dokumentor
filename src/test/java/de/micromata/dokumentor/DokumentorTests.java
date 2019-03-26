package de.micromata.dokumentor;

import static org.junit.Assert.assertEquals;

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
            "http://www.example.com/dokumentor?creator=txt&source=/hello.txt&mapping=VARIABLE:world")
        .withHeader("Fn-Http-Method", "POST")
        .enqueue();
    fn.thenRun(Dokumentor.class, "create");

    assertEquals("hello world", new String(fn.getOnlyResult().getBodyAsBytes()));
  }
}
