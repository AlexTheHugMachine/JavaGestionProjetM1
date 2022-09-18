package fr.univ_lyon1.info.m1.mes.model.builders.Patient;

public interface Builder<T extends Builder<T>> {
  T setName(String name);

  T setSurname(String surname);

  T setSSID(String ssID);

  T setAdress(String address);

  T setCity(String city);

  T getThis();
}
