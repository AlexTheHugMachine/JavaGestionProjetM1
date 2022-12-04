package fr.univ_lyon1.info.m1.mes.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import fr.univ_lyon1.info.m1.mes.constants.Constants;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.dto.prescription.PrescriptionRequestDto;

/**
 * Parse une requête passé en paramètre selon son type dans des DTO afin d'avoir
 * un langage universel dans le code entre la partie client et serveur.
 */
public interface ContentNegotiator {

  /**
   * Renvoie un DTO en fonction du contenu de la requête, si son type est
   * supporté.<br>
   * Supporte 3 types possibles : <code>json</code>,
   * <code>xml</code> et
   * <code>yml</code>.
   * Méthode utilisée par les contrôleurs permettant de lire des fichiers locaux
   * en fonction de différent types.
   *
   * @param contentType Le type de contenu qui sera utilisé pour récupérer les
   *                    données.
   * @return une instance de la classe <code>dtoType</code> ou null si le type de
   *         contenu n'est pas spécifié.
   * @throws IOException                   si le payload de la requête ne peut pas
   *                                       être lu ou s'il ne correspond pas à la
   *                                       classe du DTO
   * @throws UnsupportedOperationException si le type de fichier spécifié n'est pas supporté
   */
  static Object getDtoFromRequest(Class<?> dtoType, String contentType, String filename)
      throws UnsupportedOperationException, IOException {
    String dtoTypeName = dtoType.getSimpleName();
    switch (dtoTypeName) {
      case "PatientRequestDto":
        // Lis le fichier.
        // Créer le DTO
        Map<String, Object> patient = getParsedMapOfTheRequestedFile(
            contentType,
            Constants.getPatientPath() + filename);
        return new PatientRequestDto(
            (String) patient.get("name"),
            (String) patient.get("surname"),
            (String) patient.get("SSID"),
            (String) patient.get("adress"),
            (String) patient.get("city"));

      case "HealthProfessionalRequestDto":
        Map<String, Object> hp = getParsedMapOfTheRequestedFile(
            contentType,
            Constants.getHpPath() + filename);
        return new PatientRequestDto(
            (String) hp.get("name"),
            (String) hp.get("surname"),
            (String) hp.get("RPPS"),
            (String) hp.get("adress"),
            (String) hp.get("city"));

      case "PrescriptionRequestDto":
        Map<String, Object> prescription = getParsedMapOfTheRequestedFile(
            contentType,
            Constants.getPrescriptionPath() + filename);
        return new PrescriptionRequestDto(
            (String) prescription.get("content"),
            (String) prescription.get("quantite"),
            "", // Will be generated inside the constructor.
            (String) prescription.get("idHealthProfessionnal"),
            (String) prescription.get("idPatient"));

      default:
        throw new UnsupportedOperationException(
            "La classe " + dtoTypeName + " n'est pas reconnue par cette application.");
    }
  }

  static Map<String, Object> getParsedMapOfTheRequestedFile(String contentType, String filename)
      throws IOException, UnsupportedOperationException {
    switch (contentType) {
      case "yml":
        return YmlFileHandler.readToMap(filename);
      case "xml":
        return xmlSpecificReadProcess(filename); // WIP
      case "json":
        return jsonSpecificReadProcess(filename); // WIP
      default:
        throw new UnsupportedEncodingException("Type " + contentType + " non supporté.");
    }
  }

  static Map<String, Object> jsonSpecificReadProcess(String simpleName) {
    return null;
  }

  static Map<String, Object> xmlSpecificReadProcess(String simpleName) {
    return null;
  }

}
