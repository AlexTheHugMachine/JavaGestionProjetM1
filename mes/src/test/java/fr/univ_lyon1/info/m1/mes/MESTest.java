package fr.univ_lyon1.info.m1.mes;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.MES;

public class MESTest {
  private MES app;

  @BeforeEach
  public void setUp() {
    app = new MES();
  }

  @Test
  public void checkThatWeProperlyInitializePatientDatabase() {
    app.initializePatientDatabase();
  }

}
