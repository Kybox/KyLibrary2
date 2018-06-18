package fr.kybox.batch.job.execution.listener;

import org.springframework.batch.core.JobExecution;

public interface JobExecutionListener {

    void beforeJob(JobExecution jobExecution);
    void afterJob(JobExecution jobExecution);
}
