package fr.univ_lyon1.info.m1.mes.builders;

public interface Builder<T, S> {
  /**
   *
   * @return The builder it self to be able to chain methods rather than calling
   *         them one after an other.
   */
  T getThis();

  /**
   * Set to null every attributes of the class to be able to build multiple object
   * with the same builder.
   */
  void reset();

  /**
   * Method that will be call at the end of the method chain to build the object
   * with all attributes previously set.
   * @return <code>Object</code> that corresponds to what we wanted to build in the first place.
   */
  S build();

}
