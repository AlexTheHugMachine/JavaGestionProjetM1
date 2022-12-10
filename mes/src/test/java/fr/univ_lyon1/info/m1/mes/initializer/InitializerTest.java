package fr.univ_lyon1.info.m1.mes.initializer;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.List;

import javax.naming.NameAlreadyBoundException;

import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.constants.Constants;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class InitializerTest {
  @Test
  void initializeDAOsReturnTheListOfAllPrescriptionsStoredLocally() {
    Initializer<Prescription> initializer = new Initializer<Prescription>();
    try {
      List<Prescription> prescriptions = initializer.initializeDAOs(
          Constants.getPrescriptionPath(), Prescription.class);
    } catch (FileNotFoundException | NameAlreadyBoundException e) {
      fail("Should never throw this error.");
    }
  }
}
