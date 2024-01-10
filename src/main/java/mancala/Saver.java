package mancala;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;



public class Saver {
    private static final String ASSETS_FOLDER = "assets/";

    public static void saveObject(Serializable toSave, String filename) throws IOException {
        // Resolve the assets folder path
        Path assetsFolderPath = Paths.get(System.getProperty("user.dir")).resolve(ASSETS_FOLDER);

        // Create the folder if it doesn't exist
        if (!Files.exists(assetsFolderPath)) {
            try {
                Files.createDirectories(assetsFolderPath);
                
            } catch (IOException e) {
                throw new IOException("Failed to create folder: " + assetsFolderPath.toAbsolutePath(), e);
            }
        }

        // Create the file path
        Path filePath = assetsFolderPath.resolve(filename);

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            oos.writeObject(toSave);
        } catch (IOException e) {
            throw e;
        }
    }

    public static Serializable loadObject(String filename) throws IOException {
        // Resolve the assets folder path
        Path assetsFolderPath = Paths.get(System.getProperty("user.dir")).resolve(ASSETS_FOLDER);;

        // Create the file path
        Path filePath = assetsFolderPath.resolve(filename);

        Serializable loadedObject = null;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(filePath))) {
            loadedObject = (Serializable) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("File not found: " + filePath.toAbsolutePath(), e);
        }

        return loadedObject;
    }
}
