package fr.kybox.batch.tasklet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Kybox
 * @version 1.0
 */
@Component
public class TestTasklet {

    //private Logger logger = LogManager.getLogger(TestTasklet.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("batchJob")
    private Job job;

    public void run() {

        JobParameters parameters = new JobParametersBuilder()
                .addLong("currentTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(job, parameters);
        } catch (JobInstanceAlreadyCompleteException |
                JobExecutionAlreadyRunningException |
                JobParametersInvalidException |
                JobRestartException e) {
            e.printStackTrace();
        }

        /*
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addLong("currentTime", System.currentTimeMillis());

        System.out.println("Job object is null : " + job);

        jobLauncher.run(job, builder.toJobParameters());


        //JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        //assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        */
    }
}
