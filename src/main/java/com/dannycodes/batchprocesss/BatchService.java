package com.dannycodes.batchprocesss;

import com.dannycodes.batchprocesss.file.FileDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.batch.core.BatchStatus.COMPLETED;

@Slf4j
@RequiredArgsConstructor
@Service
public class BatchService {


    private final JobLauncher jobLauncher;
    private final Job job;
    private final FileDbService storageService;
    private final PayrollGroupRepo payrollGroupRepo;

    @Value("${batch.temporary.storage.url}")
    private String TEMP_STORAGE;

    public BatchStatus getBatch(MultipartFile multipartFile, Long productId, Long employerId, String name ) {

        try {

            storageService.storeFile(multipartFile);

            String originalFileName = multipartFile.getOriginalFilename();
            File fileToImport = new File(TEMP_STORAGE + originalFileName);
            multipartFile.transferTo(fileToImport);


            PayrollGroup payrollGroup = new PayrollGroup();

          //  if(run.getStatus().equals(COMPLETED)){

                payrollGroup.setStatus(100);
           //}else {
             //   payrollGroup.setStatus(400);
            //    throw new PayrollException("file was not uploaded successful");
           // }


            payrollGroup.setEmployerId(employerId);
            payrollGroup.setName(name);
            payrollGroup.setProductId(productId);

            PayrollGroup payrollGroupId = payrollGroupRepo.save(payrollGroup);
            log.info(payrollGroupId.getId().toString());

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("payrollGroupId",payrollGroupId.getId())
                    .addString("fullPathFileName", TEMP_STORAGE + originalFileName)
                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();

            JobExecution run = jobLauncher.run(job, jobParameters);





//        if (run.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED)){
//            Files.deleteIfExists(Paths.get(TEMP_STORAGE+originalFileName));
//        }


            return run.getStatus();
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException | IOException |
                 JobParametersInvalidException | JobRestartException | PayrollException e ) {

            throw new PayrollException(e,"error connecting,Please contact the customer service center");

        }

    }






}
