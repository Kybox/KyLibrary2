package fr.kybox.gencode;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-06-09T10:14:35.407+02:00
 * Generated source version: 3.2.4
 *
 */
@WebService(targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", name = "LibraryService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface LibraryService {

    @WebMethod
    @WebResult(name = "loginUserResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public LoginUserResponse loginUser(
        @WebParam(partName = "parameters", name = "loginUser", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        LoginUser parameters
    );

    @WebMethod
    @WebResult(name = "userBookListResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public UserBookListResponse userBookList(
        @WebParam(partName = "parameter", name = "userBookList", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        UserBookList parameter
    );

    @WebMethod
    @WebResult(name = "searchBookResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public SearchBookResponse searchBook(
        @WebParam(partName = "parameters", name = "searchBook", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        SearchBook parameters
    );
}
