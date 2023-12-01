package oleg.sichev.cloudstorage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import oleg.sichev.cloudstorage.dto.FileDTO;
import oleg.sichev.cloudstorage.entity.File;
import oleg.sichev.cloudstorage.entity.User;
import oleg.sichev.cloudstorage.exception.SaveFileException;
import oleg.sichev.cloudstorage.gzip.GzipUtils;
import oleg.sichev.cloudstorage.repository.FileRepository;
import oleg.sichev.cloudstorage.security.MyUserDetailsService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * @author OlegSichev
 */
@Service
public class FileService {

    private static FileRepository fileRepository;

    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public static void uploadFile(String authToken, String fileName, String contentType, long size, byte[] bytes) {
        try {

            File fileEntity = new File();
            fileEntity.setName(StringUtils.cleanPath(Objects.requireNonNull(fileName)));
            fileEntity.setContentType(contentType);
            fileEntity.setData(bytes);
            fileEntity.setSize(size);
            fileEntity.setUser(MyUserDetailsService.getCurrentUser());
            fileRepository.save(fileEntity);

        } catch (Exception e) {
            log.error("Cannot save file", e);
            throw new SaveFileException("Cannot save file" + " " + e.getMessage());
        }
    }


    @Transactional
    public void updateFile(String name, String newName) {
        Optional<File> getFile = fileRepository.findByNameAndUser(name, MyUserDetailsService.getCurrentUser());
        if (getFile.isPresent()) {
            getFile.get().setName(newName);

            fileRepository.save(getFile.get());
        }
    }

    @Transactional
    public Optional<File> findByName(String name) {
        Optional<File> getFile = fileRepository.findByNameAndUser(name, MyUserDetailsService.getCurrentUser());
        if (getFile.isPresent()) {
            if (!GzipUtils.isGZIPStream(getFile.get().getData())) {
                return getFile;
            }
            getFile.get().setData(GzipUtils.gzipUncompress(getFile.get().getData()));
        }
        return getFile;
    }

    @Transactional
    public List<File> findAllByUser(User user) {
        return fileRepository.findAllByUser(user);
    }

    @Transactional
    public void deleteFileByName(String name) {
        fileRepository.deleteByName(name);
    }

}