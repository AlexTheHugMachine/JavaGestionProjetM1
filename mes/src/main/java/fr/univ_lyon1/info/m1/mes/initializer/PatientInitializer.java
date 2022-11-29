package fr.univ_lyon1.info.m1.mes.initializer;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

public class PatientInitializer extends Initializer<Patient, PatientBuilder> {

  @Override
  protected Patient build(final PatientBuilder builder, final String[] row) {
    // TODO Auto-generated method stub
    return null;
  }

  /*@Override
  protected Patient build(final PatientBuilder builder, final String[] row) {
    Patient patient;
    switch (row.length) {
      case 3:
        patient = PatientsDirectorTest.constructPatientWithoutLocationInformations(row, builder);
        break;
      case 5:
        patient = PatientsDirectorTest.constructPatient(row, builder);
        break;
      default:
        throw new IllegalArgumentException("Number of element in the row array must be 3 or 5");
    }
    return patient;
  }*/
}
