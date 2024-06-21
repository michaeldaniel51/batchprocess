package com.dannycodes.batchprocesss;



import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
public class PayrollProcessor implements ItemProcessor<Payroll, Payroll> {

    @Autowired
    private PayrollGroupRepo payrollGroupRepo;

    private Long payrollGroupId;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        JobParameters jobParameters = stepExecution.getJobExecution().getJobParameters();
        payrollGroupId = jobParameters.getLong("payrollGroupId");
    }

    @Override
    public Payroll process(Payroll item) throws Exception {

       PayrollGroup payroll = payrollGroupRepo.findById(payrollGroupId).orElseThrow(()-> new PayrollException("PayrollGroup not found"));

       item.setPayrollGroup(payroll);
        log.info(item.toString());
        return item;
    }





}
