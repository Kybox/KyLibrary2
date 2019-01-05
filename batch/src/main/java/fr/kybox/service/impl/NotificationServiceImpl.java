package fr.kybox.service.impl;

import fr.kybox.gencode.LibraryService;
import fr.kybox.service.NotificationService;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static fr.kybox.utils.ValueTypes.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final LibraryService libraryService = ServiceFactory.getLibraryService();
    private final Logger log = LogManager.getLogger(this.getClass());

    @Override
    public boolean setReservationAvailable(String token, String isbn, String email) {

        int response = libraryService.setReservationNotified(token, isbn, email);

        if(response != HTTP_CODE_OK) {
            log.warn(ERROR + response);
            return false;
        }

        return true;
    }
}
