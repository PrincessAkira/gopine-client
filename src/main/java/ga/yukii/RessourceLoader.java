package ga.yukii;


import net.gopine.GopineClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RessourceLoader {
  private final static String BUILD_ASSET_DIR_PATH = "src/main/resources/";
  private final static String JAR_ASSET_DIR_PATH = "/";

  private final String filePath;

  public RessourceLoader(String filePath) {
    this.filePath = filePath;
  }

  public InputStream newStream() {
    System.out.println("Requested stream for resource " + filePath);
    System.out.println("File available: " + isAvailableAsFile());
    if(isAvailableAsFile()) {
      File file = new File(BUILD_ASSET_DIR_PATH + filePath);
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.println("Returning " + fileInputStream);
        return fileInputStream;
      } catch (FileNotFoundException ignored) {}
      throw new IllegalStateException();
    } else {
      InputStream resourceAsStream = GopineClient.class.getResourceAsStream(JAR_ASSET_DIR_PATH + filePath);
      System.out.println("Returning " + resourceAsStream);
      return resourceAsStream;
    }
  }

  public boolean isAvailableAsFile() {
    return new File(BUILD_ASSET_DIR_PATH + filePath).exists();
  }
}