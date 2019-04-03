package de.micromata.dokumentor;

import static org.junit.Assert.assertEquals;

import com.fnproject.fn.testing.FnTestingRule;
import org.junit.Rule;
import org.junit.Test;

public class XmlCreatorTests {

  @Rule public FnTestingRule fn = FnTestingRule.createDefault();

  @Test
  public void test() {
    fn.givenEvent()
        .withHeader(
            "Fn-Http-Request-Url",
            "http://www.example.com/dokumentor?creator=xml&source=hello.txt&mapping=VARIABLE:world")
        .withHeader("Fn-Http-Method", "GET")
        .enqueue();
    fn.thenRun(Dokumentor.class, "create");

    assertEquals("hello VARIABLE", new String(fn.getOnlyResult().getBodyAsBytes()));
  }
}
