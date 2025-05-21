package kg.alatoo.labor_exchange.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import kg.alatoo.labor_exchange.config.StorageProperties;
import kg.alatoo.labor_exchange.entity.User;
import kg.alatoo.labor_exchange.exception.exceptions.StorageException;
import kg.alatoo.labor_exchange.exception.exceptions.StorageFileNotFoundException;
import kg.alatoo.labor_exchange.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

  private final Path rootLocation;

  @Autowired
  public FileSystemStorageService(StorageProperties properties) {

    if (properties.getLocation().trim().isEmpty()) {
      throw new StorageException("File upload location can not be Empty.");
    }

    this.rootLocation = Paths.get(properties.getLocation());
  }

  @Override
  public void store(MultipartFile file, Path path) {
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }
      Path destinationFile = this.rootLocation.resolve(
              Paths.get(path.toString()).resolve(
                  Objects.requireNonNull(file.getOriginalFilename())))
          .normalize().toAbsolutePath();

      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath().normalize())) {
        throw new StorageException(
            "Cannot store file outside current directory.");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile,
            StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file.");
    }
  }

  public void storeProfilePicture(String userId, MultipartFile multipartFile) {
    try {
      if (multipartFile.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }

      String extension = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(
          multipartFile.getOriginalFilename().lastIndexOf("."));
      String fileName = userId + extension;
      Path destinationFile = this.rootLocation.resolve(
              Paths.get("profile_pictures").resolve(fileName))
          .normalize().toAbsolutePath();
      if (!destinationFile.getParent().startsWith(this.rootLocation.toAbsolutePath().normalize())) {
        throw new StorageException(
            "Cannot store file outside current directory.");
      }

      try (InputStream inputStream = multipartFile.getInputStream()) {
        Files.copy(inputStream, destinationFile,
            StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file.");
    }
  }

  public List<String> storeCertificates(String userId, List<MultipartFile> multipartFile) {
    try {
      List<String> fileNames = new ArrayList<>();
      for (MultipartFile file : multipartFile) {
        if (file.isEmpty()) {
          throw new StorageException("Failed to store empty file.");
        }

        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(
            file.getOriginalFilename().lastIndexOf("."));
        String fileName = userId + "_" + UUID.randomUUID().toString().substring(0, 8) + extension;
        fileNames.add(fileName);

        Path destinationFile = this.rootLocation.resolve(
                Paths.get("certificates").resolve(fileName))
            .normalize().toAbsolutePath();

        if (!destinationFile.getParent()
            .startsWith(this.rootLocation.toAbsolutePath().normalize())) {
          throw new StorageException(
              "Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
          Files.copy(inputStream, destinationFile,
              StandardCopyOption.REPLACE_EXISTING);
        }
      }
      return fileNames;
    } catch (IOException e) {
      throw new StorageException("Failed to store file.");
    }
  }


  @Override
  public Stream<Path> loadAll() {
    try (Stream<Path> paths = Files.walk(this.rootLocation, 1)) {
      return paths.filter(path -> !path.equals(this.rootLocation))
          .map(this.rootLocation::relativize);
    } catch (IOException e) {
      throw new StorageException("Failed to read stored files.");
    }
  }

  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filename);

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename);
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  @Override
  public void init() {
    try {
      if (!Files.exists(rootLocation)) {
        Files.createDirectories(rootLocation);
      }

      Path profilePicturesDir = rootLocation.resolve("profile_pictures");
      if (!Files.exists(profilePicturesDir)) {
        Files.createDirectories(profilePicturesDir);
      }
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage");
    }
  }
}