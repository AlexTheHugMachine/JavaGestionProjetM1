package fr.univ_lyon1.info.m1.mes.utils;

import java.io.InputStream;
import java.io.PrintWriter;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public interface YmlFileHandler {

  static Object readToCustomType(String path, Class<?> type)
      throws FileNotFoundException {
    InputStream inputStream = new FileInputStream(
        new File(path));
    Yaml yaml = new Yaml(new Constructor(type));
    return yaml.load(inputStream);
  }

  static Boolean writeObjectToYML(String outputPath, Object data)
      throws FileNotFoundException, IllegalArgumentException {
    if (data == null) {
      throw new IllegalArgumentException("Object given is null");
    }
    PrintWriter writer = new PrintWriter(new File(outputPath));
    DumperOptions options = new DumperOptions();
    options.setIndent(2);
    options.setPrettyFlow(true);
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    Yaml yaml = new Yaml(options);
    yaml.dump(data, writer);
    return true;
  }
}
