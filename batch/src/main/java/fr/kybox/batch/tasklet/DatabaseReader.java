package fr.kybox.batch.tasklet;

import fr.kybox.gencode.UnreturnedBook;
import fr.kybox.gencode.UnreturnedBookListResponse;
import fr.kybox.gencode.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */

@Component
public class DatabaseReader implements Tasklet, StepExecutionListener {

    private Logger logger = LogManager.getLogger(DatabaseReader.class);

    private List<UnreturnedBook> list;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("DatabaseReader before tasklet");
        list = new UnreturnedBookListResponse().getUnreturnedBook();
        User user = new User();
        user.setFirstName("Yann");
        UnreturnedBook book = new UnreturnedBook();
        book.setUser(user);
        list.add(book);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.debug("DatabaseReader execute tasklet");
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("DatabaseReader after tasklet");
        stepExecution.getJobExecution().getExecutionContext().put("list", list);
        return ExitStatus.COMPLETED;
    }
}
