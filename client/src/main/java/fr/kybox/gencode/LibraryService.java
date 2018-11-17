package fr.kybox.gencode;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-11-17T16:00:51.316+01:00
 * Generated source version: 3.2.4
 *
 */
@WebService(targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", name = "LibraryService")
@XmlSeeAlso({ObjectFactory.class})
public interface LibraryService {

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "createUserResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public CreateUserResponse createUser(
        @WebParam(partName = "parameters", name = "createUser", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        CreateUser parameters
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "loanReturnResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public LoanReturnResponse loanReturn(
        @WebParam(partName = "parameters", name = "loanReturn", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        LoanReturn parameters
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "extendBorrowingResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public ExtendBorrowingResponse extendBorrowing(
        @WebParam(partName = "parameters", name = "extendBorrowing", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        ExtendBorrowing parameters
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "reservedBookListResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public ReservedBookListResponse reservedBookList(
        @WebParam(partName = "parameters", name = "reservedBookList", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        ReservedBookList parameters
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "searchBookResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public SearchBookResponse searchBook(
        @WebParam(partName = "parameters", name = "searchBook", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        SearchBook parameters
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "loginResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public LoginResponse login(
        @WebParam(partName = "parameters", name = "login", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        Login parameters
    );

    @WebMethod
    @Oneway
    @RequestWrapper(localName = "logout", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.Logout")
    public void logout(
        @WebParam(name = "token", targetNamespace = "")
        java.lang.String token
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "getBookResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public GetBookResponse getBook(
        @WebParam(partName = "parameter", name = "getBook", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        GetBook parameter
    );

    @WebMethod
    @RequestWrapper(localName = "reserveBook", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.ReserveBook")
    @ResponseWrapper(localName = "reserveBookResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.ReserveBookResponse")
    @WebResult(name = "result", targetNamespace = "")
    public int reserveBook(
        @WebParam(name = "token", targetNamespace = "")
        java.lang.String token,
        @WebParam(name = "isbn", targetNamespace = "")
        java.lang.String isbn
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "userBookListResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public UserBookListResponse userBookList(
        @WebParam(partName = "parameter", name = "userBookList", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        UserBookList parameter
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "unreturnedBookListResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public UnreturnedBookListResponse unreturnedBookList(
        @WebParam(partName = "parameters", name = "unreturnedBookList", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        UnreturnedBookList parameters
    );
}
