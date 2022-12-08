package fr.univ_lyon1.info.m1.mes.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import fr.univ_lyon1.info.m1.mes.constants.Constants;

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
   * @throws UnsupportedOperationException si le type de fichier spécifié n'est
   *                                       pas supporté
   */
  static Object getDtoFromRequest(Class<?> dtoType, String contentType, String filename)
      throws UnsupportedOperationException, IOException {
    String dtoTypeName = dtoType.getSimpleName();
    switch (dtoTypeName) {
      case "Patient":
        return getParsedMapOfTheRequestedFile(
            contentType,
            dtoType,
            Constants.getPatientPath() + filename);
      case "HealthProfessional":
        return getParsedMapOfTheRequestedFile(
            contentType,
            dtoType,
            Constants.getHpPath() + filename);

      case "Prescription":
        return getParsedMapOfTheRequestedFile(
            contentType,
            dtoType,
            Constants.getPrescriptionPath() + filename);

      default:
        throw new UnsupportedOperationException(
            "La classe " + dtoTypeName + " n'est pas reconnue par cette application.");
    }
  }

  static Object getParsedMapOfTheRequestedFile(
      String contentType,
      Class<?> dtoType,
      String pathToFile)
      throws IOException, UnsupportedOperationException {
    switch (contentType) {
      case "yml":
        return YmlFileHandler.readToCustomType(pathToFile, dtoType);
      case "xml":
        return xmlSpecificReadProcess(pathToFile); // WIP
      case "json":
        return jsonSpecificReadProcess(pathToFile); // WIP
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
