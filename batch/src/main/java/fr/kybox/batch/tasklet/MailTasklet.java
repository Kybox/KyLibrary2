package fr.kybox.batch.tasklet;

import fr.kybox.gencode.*;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 */
@Component
public class MailTasklet implements Tasklet, StepExecutionListener {

    private Logger logger = LogManager.getLogger(MailTasklet.class);

    private ObjectFactory objectFactory;

    private List<UnreturnedBook> list;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        logger.debug("MailTasklet before tasklet");
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        this.list = (List<UnreturnedBook>) executionContext.get("list");
        logger.debug("Username = " + list.get(0).getUser().getFirstName());

        /*
        objectFactory.createUnreturnedBookList().getLogin();
        LibraryService libraryService = ServiceFactory.getLibraryService();

        UnreturnedBookList params = objectFactory.createUnreturnedBookList();
        Login login = params.getLogin();



        //libraryService.unreturnedBookList()
        */
    }


    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.debug("MailTasklet execute tasklet");
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("MailTasklet after tasklet");
        return ExitStatus.COMPLETED;
    }
}
