package fr.kybox.config;

import fr.kybox.batch.tasklet.ReminderTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableBatchProcessing
@Import({BatchScheduler.class})
public class BatchConfig {

    @Autowired public JobBuilderFactory jobBuilderFactory;
    @Autowired public StepBuilderFactory stepBuilderFactory;
    @Autowired public ReminderTasklet reminderTasklet;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }

    @Bean
    public Step step(){
        return stepBuilderFactory.get("step")
                .tasklet(reminderTasklet)
                .build();
    }
}
