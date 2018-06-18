package fr.kybox.batch.job.reporter;

import fr.kybox.batch.job.execution.listener.JobExecutionListener;
import org.springframework.batch.core.JobExecution;

/**
 * @author Kybox
 * @version 1.0
 */
public class JobReporter implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
