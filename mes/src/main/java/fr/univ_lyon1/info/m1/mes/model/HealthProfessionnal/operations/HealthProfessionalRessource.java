package fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations;


import java.io.Serializable;
import java.util.Collection;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.dto.healthprofessional.HealthProfessionalRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.ressources.RessourceInterface;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

/**
 * Réalise les opérations "simples" CRUD de gestion des ressources de type
 * <code>HealthProfessionnal</code>. <br>
 * Cette classe est créer et utilisée par le contrôleur de ressources
 * <code>HealthProfessionnalController</code> qui lui injected le DAO et le
 * builder
 * pour créer des patients.
 */
public class HealthProfessionalRessource
    implements RessourceInterface<HealthProfessionalRequestDto, HealthProfessional> {
  private final HealthProfessionalDAO hpDao;
  private final HealthProfessionalBuilder builder;

  public HealthProfessionalRessource(
      final HealthProfessionalDAO hpDao,
      final HealthProfessionalBuilder builder) {
    this.hpDao = hpDao;
    this.builder = builder;
  }

  @Override
  public Serializable create(final HealthProfessionalRequestDto element)
      throws NameAlreadyBoundException {
    String name = element.getName();
    String surname = element.getSurname();
    String rpps = element.getRPPS();
    HPSpeciality speciality = HPSpeciality.valueOf(element.getSpeciality());

    HealthProfessional hp = builder
        .setName(name)
        .setSurname(surname)
        .setRPPS(rpps)
        .setSpeciality(speciality)
        .build();

    hpDao.add(hp);
    return hp.getRPPS();
  }

  @Override
  public HealthProfessional readOne(final Serializable key)
      throws NameNotFoundException {
    ArgumentChecker.checkStringNotNullOrEmpty(key.toString());
    try {
      return hpDao.findOne(key);
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("No health professional found.");
    }
  }

  @Override
  public Boolean update(final HealthProfessionalRequestDto element)
    throws IllegalArgumentException {
    String name = element.getName();
    String surname = element.getSurname();
    String rpps = element.getRPPS();
    HPSpeciality speciality = HPSpeciality.valueOf(element.getSpeciality());

    HealthProfessional hp = builder
        .setName(name)
        .setSurname(surname)
        .setRPPS(rpps)
        .setSpeciality(speciality)
        .build();

    hpDao.update(rpps, hp);
    return true;
  }

  @Override
  public Boolean deleteById(final Serializable key) throws NameNotFoundException {
    try {
      ArgumentChecker.checkStringNotNullOrEmpty(key.toString());
      hpDao.deleteById(key);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("The id provided is null or empty");
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("This Health Professinal does not exist.");
    }
    return true;
  }

  @Override
  public void delete(final HealthProfessionalRequestDto element)
      throws NameNotFoundException {
    try {
      HealthProfessional storedHP = hpDao.findOne(element.getRPPS());
      hpDao.delete(storedHP);
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("This Health Professional does not exist.");
    }
  }

  @Override
  public Collection<HealthProfessional> readAll() {
    return hpDao.findAll();
  }
}
