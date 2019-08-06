package visloukh.libra.Service.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import visloukh.libra.Service.FileStorageService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    public final String PATH = System.getProperty("user.dir");
    public final String SEPARATOR = System.getProperty("file.separator");

//    private final Path fileStorageLocationUsers;
//    private final Path fileStorageLocationBooks;
//    private final Path fileStorageLocationChapters;

    public FileStorageServiceImpl() {
//        String uploadUsersDir = PATH + SEPARATOR + "uploads"+SEPARATOR+"users";
//        String uploadBooksDir = PATH + SEPARATOR + "uploads"+SEPARATOR+"books";
//        String uploadСhaptersDir = uploadBooksDir+SEPARATOR+"chapters";
//        System.out.println(uploadUsersDir);
//        System.out.println(uploadBooksDir);
//        System.out.println(uploadСhaptersDir);
//
//        this.fileStorageLocationUsers = Paths.get(uploadUsersDir)
//                .toAbsolutePath().normalize();
//        this.fileStorageLocationBooks = Paths.get(uploadBooksDir)
//                .toAbsolutePath().normalize();
//        this.fileStorageLocationChapters = Paths.get(uploadСhaptersDir)
//                .toAbsolutePath().normalize();
//
//        try {
//            Files.createDirectories(this.fileStorageLocationUsers);
//            Files.createDirectories(this.fileStorageLocationBooks);
//            Files.createDirectories(this.fileStorageLocationChapters);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public String createLocation (String pathToStorage){
        String uploadDir = PATH + SEPARATOR + "uploads";

        if (pathToStorage.lastIndexOf(SEPARATOR)!=-1) {
            String[] partsOfPath = pathToStorage.split("[/]");
            System.out.println(partsOfPath);
            for (int i = 0; i <partsOfPath.length ; i++) {
                System.out.println(partsOfPath[i]+ " in circle");
                uploadDir+= SEPARATOR+ partsOfPath[i];
              //  System.out.println(uploadDir + " create location circle");
            }

        } else {
            uploadDir+= SEPARATOR+ pathToStorage;
        }
        System.out.println(uploadDir + " create location");
        Path fileStorageLocation =Paths.get(uploadDir)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadDir;
    }

    @Override
    public String storeFile(MultipartFile file, int id, String pathToStore) {
//        System.out.println(pathToStore.lastIndexOf(SEPARATOR));
        String uploadDir = createLocation(pathToStore);
//        System.out.println(pathToStore+ " path to store");
//        System.out.println(uploadDir+ " upload dir");
        String fileName = file.getOriginalFilename();
       // System.out.println(fileName + " file name");
        String extention="";
        int i = fileName.lastIndexOf('.');
        if (i>0){
            extention=fileName.substring(i);
        }

        String lastDirName = pathToStore;
        if (pathToStore.lastIndexOf(SEPARATOR)!=-1)
         lastDirName=pathToStore.substring(pathToStore.lastIndexOf(SEPARATOR)+1);
        System.out.println(lastDirName);
        String newFileName = lastDirName + id+ "" + extention;

        System.out.println(newFileName);
        Path targetLocation;

            try {
                targetLocation = Paths.get(uploadDir)
                        .toAbsolutePath().normalize()
                        .resolve(newFileName);
                Files.copy(file.getInputStream(), targetLocation,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }



        return newFileName;
    }

    @Override
    public Resource loadFile(String fileName, String pathToStore) {
        String uploadDir=createLocation(pathToStore);
        Path filePath = Paths.get(uploadDir)
                    .toAbsolutePath().normalize()
                    .resolve(fileName);

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
