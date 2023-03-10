package fr.univ_lyon1.info.m1.mes.constants;

/**
 * Class of global variables to store data about the application that could be
 * used by multiples classes.
 */
public final class Constants {
  private static final String LOCAL_PATH = "./ressources/data/";
  private static final String PATIENT_PATH = LOCAL_PATH + "Patient/";
  private static final String HP_PATH = LOCAL_PATH + "HealthProfessional/";
  private static final String PRESCRIPTION_PATH = LOCAL_PATH + "Prescription/";
  private static final String LOG_PATH = "./src/main/log/";
  private static final String DATA_TEST = "./src/test/java/fr/univ_lyon1/info/m1/mes/data/";

  private Constants() {
  }

  public static String getLocalPath() {
    return LOCAL_PATH;
  }

  public static String getPatientPath() {
    return PATIENT_PATH;
  }

  public static String getHpPath() {
    return HP_PATH;
  }

  public static String getPrescriptionPath() {
    return PRESCRIPTION_PATH;
  }

  public static String getLogPath() {
    return LOG_PATH;
  }

  public static String getDataTestPath() {
    return DATA_TEST;
  }
}
