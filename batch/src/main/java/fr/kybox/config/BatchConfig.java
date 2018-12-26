package fr.kybox.config;

import fr.kybox.batch.tasklet.UnreturnedTasklet;
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
@Import({UnreturnedScheduler.class, ReservationScheduler.class})
public class BatchConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    public final UnreturnedTasklet unreturnedTasklet;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       UnreturnedTasklet unreturnedTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.unreturnedTasklet = unreturnedTasklet;
    }

    @Bean
    public Job jobReminder(){
        return jobBuilderFactory.get("jobReminder")
                .start(stepReminder())
                .build();
    }

    @Bean
    public Step stepReminder(){
        return stepBuilderFactory.get("stepReminder")
                .tasklet(unreturnedTasklet)
                .build();
    }
}
