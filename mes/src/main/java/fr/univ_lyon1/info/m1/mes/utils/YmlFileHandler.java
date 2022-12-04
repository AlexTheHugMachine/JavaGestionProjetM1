package fr.univ_lyon1.info.m1.mes.utils;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public interface YmlFileHandler<T> {
  static Map<String, Object> readToMap(String fileName)
      throws FileNotFoundException {
    InputStream inputStream = new FileInputStream(
        new File(fileName));
    Yaml yaml = new Yaml();
    Map<String, Object> data = yaml.load(inputStream);
    return data;
  }

}
