package fr.kybox.config;

import fr.kybox.batch.ExpirationScheduler;
import fr.kybox.batch.ReservationScheduler;
import fr.kybox.batch.UnreturnedScheduler;
import fr.kybox.batch.tasklet.ExpirationTasklet;
import fr.kybox.batch.tasklet.ReservationTasklet;
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
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
@EnableScheduling
@EnableBatchProcessing
@Import({UnreturnedScheduler.class, ReservationScheduler.class, ExpirationScheduler.class})
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UnreturnedTasklet unreturnedTasklet;
    private final ReservationTasklet reservationTasklet;
    private final ExpirationTasklet expirationTasklet;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       UnreturnedTasklet unreturnedTasklet,
                       ReservationTasklet reservationTasklet,
                       ExpirationTasklet expirationTasklet) {

        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.unreturnedTasklet = unreturnedTasklet;
        this.reservationTasklet = reservationTasklet;
        this.expirationTasklet = expirationTasklet;
    }

    @Bean
    public TaskScheduler taskScheduler(){
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public Job jobUnreturned(){
        return jobBuilderFactory.get("jobUnreturned")
                .start(stepUnreturned())
                .build();
    }

    @Bean
    public Step stepUnreturned(){
        return stepBuilderFactory.get("stepUnreturned")
                .tasklet(unreturnedTasklet)
                .build();
    }

    @Bean
    public Job jobReservation(){
        return jobBuilderFactory.get("jobReservation")
                .start(stepReservation())
                .build();
    }

    @Bean
    public Step stepReservation(){
        return stepBuilderFactory.get("stepReservation")
                .tasklet(reservationTasklet)
                .build();
    }

    @Bean
    public Job jobExpiration(){
        return jobBuilderFactory.get("jobExpiration")
                .start(stepExpiration())
                .build();
    }

    @Bean
    public Step stepExpiration(){
        return stepBuilderFactory.get("stepExpiration")
                .tasklet(expirationTasklet)
                .build();
    }
}
