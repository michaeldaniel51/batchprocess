package com.dannycodes.batchprocesss;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@Slf4j
@RequiredArgsConstructor
@RestController
public class BatchApi {



    private final BatchService batchService;

    @PostMapping("/startBatch")
    public BatchStatus getBatch(@RequestParam("file") MultipartFile multipartFile, @RequestParam Long productId, @RequestParam Long employerId,@RequestParam String name ) {

        return batchService.getBatch(multipartFile,productId,employerId,name);

}

}