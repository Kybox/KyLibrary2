package fr.kybox.batch.tasklet;

import fr.kybox.batch.result.BatchResult;
import fr.kybox.gencode.LibraryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.repeat.RepeatStatus;

import static fr.kybox.utils.ValueTypes.ADMIN_TOKEN_REMOVED;
import static fr.kybox.utils.ValueTypes.ERROR;
import static fr.kybox.utils.ValueTypes.LOGOUT;

public class AbstractTasklet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    RepeatStatus executionError(int resultCode, String token, LibraryService libraryService){

        BatchResult.put(ERROR, resultCode);
        libraryService.logout(token);
        BatchResult.put(LOGOUT, ADMIN_TOKEN_REMOVED);
        return RepeatStatus.FINISHED;
    }
}
