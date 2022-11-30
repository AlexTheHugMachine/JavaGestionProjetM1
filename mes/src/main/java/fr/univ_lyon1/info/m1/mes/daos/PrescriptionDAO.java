package fr.univ_lyon1.info.m1.mes.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class PrescriptionDAO extends AbstractMapDao<Prescription> {

  @Override
  protected Serializable getKeyForElement(final Prescription element) {
    return element.getId();
  }

  @Override
  public void update(final Serializable id, final Prescription element)
      throws  IllegalArgumentException {
    try {
      Prescription prescriptionStored = this.findOne(id);

      String idPatientStored = prescriptionStored.getIdPatient();
      String idHpStored = prescriptionStored.getIdHealthProfessional();

      if (!(idPatientStored.equals(element.getIdPatient())
          && idHpStored.equals(element.getIdHealthProfessional()))) {
        throw new IllegalArgumentException("Prescription does not match stored ids.");
      }
      deleteById(id);
      super.update(element.getId(), element);
    } catch (NameNotFoundException e) {
      throw new IllegalArgumentException("Prescription does not exist");
    }
  }

  /**
   *
   * @param idPatient L'id du patient dont on veut les prescriptions.
   * @param idHp      Le professionnel de santé qui a prescris ces dernieres.
   * @return Toutes les prescriptions qu'un professionnel de santé à donner à un
   *         patient.
   * @throws NameNotFoundException "No prescriptions found."
   */
  public List<Prescription> findByHpIdGivenThePatientId(final String idPatient, final String idHp)
      throws NameNotFoundException {
    List<Prescription> prescriptions = new ArrayList<Prescription>();
    this.getCollection().values().forEach(p -> {
      if (p.getIdHealthProfessional() == idHp && p.getIdPatient() == idPatient) {
        prescriptions.add(p);
      }
    });
    if (prescriptions.isEmpty()) {
      throw new NameNotFoundException("No prescriptions found.");
    }
    return prescriptions;
  }

  /**
   *
   * @param idPatient L'id du patient dont on cherche les prescriptions.
   * @return Toutes les prescriptions donnés à un patient.
   * @throws NameNotFoundException
   */
  public List<Prescription> findByPatientId(final String idPatient)
      throws NameNotFoundException {
    List<Prescription> prescriptions = new ArrayList<Prescription>();
    this.getCollection().values().forEach(p -> {
      if (p.getIdPatient().equals(idPatient)) {
        prescriptions.add(p);
      }
    });
    if (prescriptions.isEmpty()) {
      throw new NameNotFoundException("No prescriptions found.");
    }
    return prescriptions;
  }

  public List<Prescription> findByContent(final String contentToSearch)
      throws NameNotFoundException {
    List<Prescription> matchesPrescriptions = new ArrayList<Prescription>();
    this.getCollection().values().forEach(
        p -> {
          String pContent = p.getContent().toUpperCase();
          if (pContent.equals(contentToSearch.toUpperCase())) {
            matchesPrescriptions.add(p);
          }
        });
    if (matchesPrescriptions.isEmpty()) {
      throw new NameNotFoundException("No matches found for " + contentToSearch);
    }
    return matchesPrescriptions;
  }
}
