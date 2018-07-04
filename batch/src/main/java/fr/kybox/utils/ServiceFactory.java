package fr.kybox.utils;

import fr.kybox.gencode.LibraryService;
import fr.kybox.gencode.LibraryWebService;

/**
 * @author Kybox
 * @version 1.0
 */
public class ServiceFactory {

    private static LibraryService service;
    private static LibraryWebService webService;

    public static LibraryService getLibraryService(){

        if(service == null) initService();
        return service;
    }

    private static void initService(){

        if(webService == null) webService = new LibraryWebService();
        service = webService.getLibraryServicePort();
    }
}
