package fr.kybox.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@PropertySource("classpath:application.properties")
public class UnreturnedScheduler {

    @Autowired private JobLauncher jobLauncher;

    @Qualifier("jobUnreturned")
    @Autowired private Job job;

    @Value("${unreturned.cron}") String cron;

    @Scheduled(cron = "${unreturned.cron}")
    public void unreturnedScheduler()
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();

        jobLauncher.run(job, jobParameters);

    }
}
