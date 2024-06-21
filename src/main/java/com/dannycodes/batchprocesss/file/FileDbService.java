package com.dannycodes.batchprocesss.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileDbService {


    @Autowired
    private FileDbRepository fileDbRepository;

    public FileDb storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDb FileDb = new FileDb(fileName, file.getContentType(), file.getBytes());

        return fileDbRepository.save(FileDb);
    }

    public FileDb getFile(Long id) {
        return fileDbRepository.findById(id).get();
    }

    public Stream<FileDb> getAllFiles() {
        return fileDbRepository.findAll().stream();
    }







}
