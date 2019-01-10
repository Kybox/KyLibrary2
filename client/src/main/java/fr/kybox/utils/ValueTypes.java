package fr.kybox.utils;

public class ValueTypes {

    public final static String LEVEL_ADMIN = "admin";
    public final static String LEVEL_MANAGER = "manager";
    public final static String LEVEL_CLIENT = "client";
    public final static String TOKEN = "token";

    public final static String ERROR_BAD_ISBN = "Livre introuvable / ISBN incorrect";
    public final static String ERROR_LOGIN_EMPTY_FIELD = "Aucun des champs ne doit être vide !";
    public final static String ERROR_LOGIN_BAD_CREDIENTIALS = "Identifiants d'authentification incorrects !";
    public final static String ERROR_USER_NOT_FOUND = "Aucun utilisateur n'a été trouvé.";

    public final static String ERROR_MSG_NOT_FOUND = "Ressource non trouvée.";
    public final static String ERROR_MSG_BAD_REQUEST = "La syntaxe de la requête est erronée.";
    public final static String ERROR_MSG_TOKEN_EXPIRED = "Le token a expiré ou est invalide.";
    public final static String ERROR_MSG_FORBIDDEN = "Les droits d'accès ne permettent pas d'accéder à la ressource.";

    public final static String REDIRECT_403 = "error-forbidden";

    public final static int HTTP_CODE_OK = 200;
    public final static int HTTP_CODE_BAD_REQUEST = 400;
    public final static int HTTP_CODE_FORBIDDEN = 403;
    public final static int HTTP_CODE_NOT_FOUND = 404;
    public final static int HTTP_CODE_TOKEN_EXPIRED_INVALID = 498;
}
