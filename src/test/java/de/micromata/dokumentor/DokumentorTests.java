package de.micromata.dokumentor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DokumentorTests {
  @Test
  void test() {
    Assertions.assertEquals("Dokumentor", Dokumentor.class.getSimpleName());
  }
}
