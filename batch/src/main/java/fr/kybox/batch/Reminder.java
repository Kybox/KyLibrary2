package fr.kybox.batch;

import fr.kybox.batch.utils.BatchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Kybox
 * @version 1.0
 */
@Component
public class Reminder {

    private Logger logger = LogManager.getLogger(Reminder.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("batchJob")
    private Job job;

    public Map<String, Object> run(String email, String password) {

        JobParameters parameters = new JobParametersBuilder()
                .addLong("currentTime", System.currentTimeMillis())
                .addString("email", email)
                .addString("password", password)
                .toJobParameters();

        JobExecution jobExecution = null;

        try { jobExecution = jobLauncher.run(job, parameters); }
        catch (JobInstanceAlreadyCompleteException |
                JobExecutionAlreadyRunningException |
                JobParametersInvalidException |
                JobRestartException e) {

            logger.error(e.getMessage());
            BatchResult.map.put("Erreur", e.getMessage());
        }

        if (jobExecution != null)
            BatchResult.map.put("EndTime", jobExecution.getEndTime());

        return BatchResult.map;
    }


    public void scheduleder(){

        run("admin@kylibrary.fr", "admin");
    }
}
