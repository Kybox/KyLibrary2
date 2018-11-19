package fr.kybox.service.utils;

import fr.kybox.gencode.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Kybox
 * @version 1.0
 */
public class CheckParams {

    private static final Logger logger = LogManager.getLogger(CheckParams.class);

    public static boolean login(Login params){

        return checkSignInParams(params.getLogin(), params.getPassword());
    }

    public static boolean createUser(CreateUser params){

        if(params != null && params.getLogin() != null) {
            Login login = params.getLogin();
            return checkSignInParams(login.getLogin(), login.getPassword());
        }
        else return false;
    }

    public static boolean loanReturn(LoanReturn params){

        if(params != null && params.getLogin() != null){
            Login login = params.getLogin();
            if(checkSignInParams(login.getLogin(), login.getPassword()))
                return checkUserData(params.getUser()) && checkBookBorrowedData(params.getBookBorrowed());

            else return false;
        }
        else return false;
    }

    private static boolean checkSignInParams(String login, String pass){
        return login != null && !login.isEmpty() && pass != null && !pass.isEmpty();
    }

    private static boolean checkUserData(User user){
        return user.getEmail() != null && !user.getEmail().isEmpty();
    }

    private static boolean checkBookBorrowedData(BookBorrowed bookBorrowed){
        return bookBorrowed.getReturnDate() != null && checkBookData(bookBorrowed.getBook());
    }

    private static boolean checkBookData(Book book){
        return book.getIsbn() != null && !book.getIsbn().isEmpty();
    }
}
