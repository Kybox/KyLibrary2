package fr.kybox.gencode;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * Provide services from KyLibrary
 *         
 *
 * This class was generated by Apache CXF 3.2.4
 * 2019-01-05T23:40:20.729+01:00
 * Generated source version: 3.2.4
 *
 */
@WebServiceClient(name = "LibraryWebService",
                  wsdlLocation = "https://kybox.fr/kylibrary/wsdl/LibraryService.wsdl",
                  targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
public class LibraryWebService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("dd7b026a-d6a2-4089-adb2-596ab0598c73", "LibraryWebService");
    public final static QName LibraryServicePort = new QName("dd7b026a-d6a2-4089-adb2-596ab0598c73", "LibraryServicePort");
    static {
        URL url = null;
        try {
            url = new URL("https://kybox.fr/kylibrary/wsdl/LibraryService.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(LibraryWebService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "https://kybox.fr/kylibrary/wsdl/LibraryService.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public LibraryWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public LibraryWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LibraryWebService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public LibraryWebService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public LibraryWebService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public LibraryWebService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns LibraryService
     */
    @WebEndpoint(name = "LibraryServicePort")
    public LibraryService getLibraryServicePort() {
        return super.getPort(LibraryServicePort, LibraryService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns LibraryService
     */
    @WebEndpoint(name = "LibraryServicePort")
    public LibraryService getLibraryServicePort(WebServiceFeature... features) {
        return super.getPort(LibraryServicePort, LibraryService.class, features);
    }

}
