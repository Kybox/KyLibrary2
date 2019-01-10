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
 * 2019-01-10T10:55:01.450+01:00
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
    @RequestWrapper(localName = "loanReturn", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.LoanReturn")
    @ResponseWrapper(localName = "loanReturnResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.LoanReturnResponse")
    @WebResult(name = "result", targetNamespace = "")
    public int loanReturn(
        @WebParam(name = "token", targetNamespace = "")
        java.lang.String token,
        @WebParam(name = "isbn", targetNamespace = "")
        java.lang.String isbn,
        @WebParam(name = "email", targetNamespace = "")
        java.lang.String email
    );

    @WebMethod
    @RequestWrapper(localName = "setReservationNotified", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.SetReservationNotified")
    @ResponseWrapper(localName = "setReservationNotifiedResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.SetReservationNotifiedResponse")
    @WebResult(name = "result", targetNamespace = "")
    public int setReservationNotified(
        @WebParam(name = "token", targetNamespace = "")
        java.lang.String token,
        @WebParam(name = "isbn", targetNamespace = "")
        java.lang.String isbn,
        @WebParam(name = "email", targetNamespace = "")
        java.lang.String email
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
    @WebResult(name = "searchBorrowersByIsbnResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public SearchBorrowersByIsbnResponse searchBorrowersByIsbn(
        @WebParam(partName = "parameters", name = "searchBorrowersByIsbn", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        SearchBorrowersByIsbn parameters
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
    @WebResult(name = "unreturnedBookListResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public UnreturnedBookListResponse unreturnedBookList(
        @WebParam(partName = "parameters", name = "unreturnedBookList", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        UnreturnedBookList parameters
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "cancelReservationResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public CancelReservationResponse cancelReservation(
        @WebParam(partName = "parameters", name = "cancelReservation", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        CancelReservation parameters
    );

    @WebMethod
    @RequestWrapper(localName = "updateAlertSenderStatus", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.UpdateAlertSenderStatus")
    @ResponseWrapper(localName = "updateAlertSenderStatusResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.UpdateAlertSenderStatusResponse")
    @WebResult(name = "result", targetNamespace = "")
    public int updateAlertSenderStatus(
        @WebParam(name = "token", targetNamespace = "")
        java.lang.String token,
        @WebParam(name = "status", targetNamespace = "")
        boolean status
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "searchUserResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public SearchUserResponse searchUser(
        @WebParam(partName = "parameters", name = "searchUser", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        SearchUser parameters
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "searchLoansAboutToExpireResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public SearchLoansAboutToExpireResponse searchLoansAboutToExpire(
        @WebParam(partName = "parameter", name = "searchLoansAboutToExpire", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        SearchLoansAboutToExpire parameter
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
    @WebResult(name = "registerNewLoanResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public RegisterNewLoanResponse registerNewLoan(
        @WebParam(partName = "parameters", name = "registerNewLoan", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        RegisterNewLoan parameters
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
    @RequestWrapper(localName = "checkToken", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.CheckToken")
    @ResponseWrapper(localName = "checkTokenResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", className = "fr.kybox.gencode.CheckTokenResponse")
    @WebResult(name = "result", targetNamespace = "")
    public int checkToken(
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
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "userBookListResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public UserBookListResponse userBookList(
        @WebParam(partName = "parameter", name = "userBookList", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        UserBookList parameter
    );

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "checkReservedBooksResponse", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73", partName = "response")
    public CheckReservedBooksResponse checkReservedBooks(
        @WebParam(partName = "parameter", name = "checkReservedBooks", targetNamespace = "dd7b026a-d6a2-4089-adb2-596ab0598c73")
        CheckReservedBooks parameter
    );
}
