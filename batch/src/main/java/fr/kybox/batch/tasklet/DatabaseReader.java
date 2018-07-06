package fr.kybox.batch.tasklet;

import fr.kybox.batch.utils.BatchResult;
import fr.kybox.gencode.*;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.*;
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

    private List<UnreturnedBook> itemList;
    private UnreturnedBookList request;
    private UnreturnedBookListResponse response;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        logger.debug("DatabaseReader before tasklet");

        BatchResult.put("StartTime", stepExecution.getJobExecution().getStartTime());

        String email = stepExecution.getJobParameters().getString("email");
        String password = stepExecution.getJobParameters().getString("password");

        ObjectFactory objectFactory = new ObjectFactory();

        request = objectFactory.createUnreturnedBookList();
        Login login = objectFactory.createLogin();
        login.setLogin(email);
        login.setPassword(password);
        request.setLogin(login);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        logger.debug("DatabaseReader execute tasklet");

        BatchResult.put("Authentication", "Attempt to connect");
        LibraryService libraryService = ServiceFactory.getLibraryService();
        response = libraryService.unreturnedBookList(request);
        BatchResult.put("LoginCode", response.getResult());

        if(response.getResult() == 200) {
            BatchResult.put("UnreturnedBookList", "Retrieved");
            BatchResult.put("Total unreturned books", response.getUnreturnedBook().size());
            itemList = response.getUnreturnedBook();
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("DatabaseReader after tasklet");
        stepExecution.getJobExecution().getExecutionContext().put("itemList", itemList);
        return ExitStatus.COMPLETED;
    }
}
