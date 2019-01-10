package fr.kybox.utils;

public class ValueTypes {

    public static final int HTTP_CODE_OK = 200;
    public static final int HTTP_CODE_TOKEN_EXPIRED_INVALID = 498;

    public static final int ADMIN_LEVEL = 1;

    public static final boolean UNAUTHORIZED = false;
    public static final boolean AUTHORIZED = true;

    public static final String BATCH = "batch";
    public static final String DEFAULT = "default";
    public static final String UNRETURNED = "unreturned";
    public static final String RESERVATION = "reservation";
    public static final String EXPIRATION = "expiration";

    public static final String JSP_TITLE = "jspTitle";
    public static final String JSP_INFO = "jspInfo";
    public static final String JSP_RESULT_INFO = "jspResultInfo";
    public static final String JSP_MODAL_DESC = "jspModalDesc";
    public static final String JSP_UNRETURNED_TITLE = "Batch - Ouvrage non rendus";
    public static final String JSP_UNRETURNED_INFO = "Lancer le batch d'envoi d'e-mails aux utilisateurs dont n'ayant pas retourné leurs emprunts à temps.";
    public static final String JSP_UNRETURNED_RESULT_INFO = "Résultats du batch d'envoi d'e-mails aux utilisateurs n'ayant pas retourné leurs emprunts à temps.";
    public static final String JSP_UNRETURNED_MODAL_DESC = "Processus de récupération des ouvrages non rendus";
    public static final String JSP_RESERVATION_TITLE = "Batch - Réservations disponibles";
    public static final String JSP_RESERVATION_INFO = "Lancer le batch d'envoi d'e-mails aux utilisateurs dont la réservation est disponible.";
    public static final String JSP_RESERVATION_RESULT_INFO = "Résultats du batch d'envoi d'e-mails aux utilisateurs dont la réservation est disponible.";
    public static final String JSP_RESERVATION_MODAL_DESC = "Processus de vérification des réservations disponibles";
    public static final String JSP_EXPIRATION_TITLE = "Batch - Prêts arrivant à expiration";
    public static final String JSP_EXPIRATION_INFO = "Lancer le batch d'envoi d'e-mails aux utilisateurs dont les emprunts arrivent à expiration.";
    public static final String JSP_EXPIRATION_RESULT_INFO = "Résultats du batch d'envoi d'e-mails aux utilisateurs dont les emprunts arrivent à expiration.";
    public static final String JSP_EXPIRATION_MODAL_DESC = "Processus de notification des prêts arrivant à expiration";

    public static final String ISBN = "Isbn : ";
    public static final String BOOK_TITLE = "Title : ";
    public static final String AUTHOR = "Author : ";
    public static final String BY = " by ";

    public static final String LINE_BREAK = "\n";
    public static final String DOUBLE_LINE_BREAK = "\n\n";
    public static final String SPACE = " ";
    public static final String SLASH = " / ";
    public static final String HYPHEN = " - ";

    public static final String RESERVATION_TITLE = "Reservation ";
    public static final String RESERVATION_DATE = "Reservation date ";
    public static final String TOKEN = "Token";
    public static final String UNAUTHORIZED_MSG = "Vous n'êtes pas autorisé à lancer le batch";
    public static final String START_TIME = "Start time";
    public static final String AUTHENTICATION = "Authentication";
    public static final String ATTEMPT_TO_CONNECT = "Attempt to connect";
    public static final String ERROR = "Error ";
    public static final String LOGIN_FAILED = "Login failed";
    public static final String CONNECTED = "Connected";
    public static final String LOGIN_SUCCESS = "Login success";
    public static final String NO_BOOK_FOUND = "No book found";
    public static final String BOOK_LIST_IS_EMPTY = "The book list is empty";
    public static final String USER = "User";
    public static final String BOOK = "Book ";
    public static final String ATTEMPT_TO_SEND = "Attempt to send";
    public static final String TO = "To ";
    public static final String SUCCESSFUL_SENDING = "Successful sending";
    public static final String NOTIFICATION_SENT = "The notification has been sent";
    public static final String SENDING_FAIL = "Sending fail";
    public static final String NOTIFICATION_NOT_SENT = "The notification was not sent";
    public static final String LOGOUT = "Logout";
    public static final String ADMIN_TOKEN_REMOVED = "The admin token has been removed";
    public static final String BATCH_PROCESSING = "Batch processing";
    public static final String COMPLETED = "Completed";
    public static final String END_TIME = "End time";
    public static final String BATCH_RESULT = "batchResult";
    public static final String LOGIN_FORM = "loginForm";
    public static final String BOOK_RESERVED = "Reservation";
    public static final String ON = "On ";
    public static final String AT = " at ";
    public static final String NOTIFICATION_REGISTRATION = "Notification registration";
    public static final String NOTIFICATION_FAILED = "Failed";
    public static final String NOTIFICATION_SUCCESS = "Success";
    public static final String USER_ALREADY_NOTIFIED = "User already notified";
    public static final String REMAINING_TIME_BEFORE_DELETION = "Remaining time before deletion";
    public static final String APPROXIMATELY = "Approximately ";
    public static final String HOURS = " hours";
    public static final String MINUTES = " minutes";
    public static final String CANCEL_RESERVATION = "Cancel reservation";
    public static final String ATTEMPT_TO_CANCEL_RESERVATION = "Attempt to cancel reservation";
    public static final String CANCEL_RESERVATION_ERROR = "the reservation could not be canceled";
    public static final String NEXT_RESERVATION = "Next reservation";
    public static final String ANOTHER_RESERVATION_EXISTS = "There is another book reservation";
    public static final String NO_OTHER_RESERVATION = "There is no other reservation";
    public static final String ADDING_A_RESERVATION = "Adding a reservation";
    public static final String ADDING_THE_NEW_RESERVATION = "Adding the new reservation in the batch";
    public static final String LOAN = "Loan ";
    public static final String DAYS = "jours";
    public static final String DAY = "jour";
    public static final String CHECK_RESERVATIONS = "Check reservations";
    public static final String CHECKING_COMPLETED = "Checking completed";
    public static final String CHECKING_CURRENT_LOANS = "Checking current loans";
    public static final String LOAN_STATUS = "Status of the loan";
    public static final String LOAN_NOT_RETURNED = "The loan has not been returned";
    public static final String PROCESS_DELEGATION = "Process delegation";
    public static final String BATCH_OF_UNRETURNED_BOOKS = "Batch of unreturned books";
    public static final String TIME_TO_EXPIRY = "Time to expiry of the loan";
    public static final String LOAN_EXPIRE_IN = "the loan expires in";
}
