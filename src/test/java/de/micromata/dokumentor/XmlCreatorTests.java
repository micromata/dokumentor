package de.micromata.dokumentor;

import static org.junit.Assert.assertEquals;

import com.fnproject.fn.testing.FnTestingRule;
import de.micromata.dokumentor.xml.XmlCreator;
import org.junit.Rule;
import org.junit.Test;

public class XmlCreatorTests {

  @Rule public FnTestingRule fn = FnTestingRule.createDefault();

  @Test
  public void test() {
    fn.givenEvent()
        .withHeader(
            "Fn-Http-Request-Url",
            "http://www.example.com/dokumentor?creator=xml&source=hello.docx&mapping=Variable:world")
        .withHeader("Fn-Http-Method", "GET")
        .enqueue();
    fn.thenRun(XmlCreator.class, "create");

    assertEquals("hello VARIABLE", new String(fn.getOnlyResult().getBodyAsBytes()));
  }
}
