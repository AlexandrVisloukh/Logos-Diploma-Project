package visloukh.libra.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile file, int id, String pathToStorage);

    Resource loadFile(String fileName, String pathToStorage);
}
