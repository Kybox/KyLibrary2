package fr.kybox.utils;

import fr.kybox.entities.BookEntity;
import fr.kybox.entities.Level;
import fr.kybox.entities.ReservedBook;
import fr.kybox.entities.UserEntity;
import fr.kybox.gencode.Book;
import fr.kybox.gencode.ObjectFactory;
import fr.kybox.gencode.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;

import static fr.kybox.utils.ValueTypes.*;

/**
 * @author Kybox
 * @version 1.0
 */
public class Reflection {

    private final static Logger logger = LogManager.getLogger(Reflection.class);

    public static Object WStoEntity(Object wsObject){

        Object objEntity = null;

        if(wsObject instanceof Book) objEntity = new BookEntity();
        else if(wsObject instanceof User) objEntity = new UserEntity();

        for(Method method : wsObject.getClass().getDeclaredMethods()){

            Object object;
            Method currentMethod;

            try {

                String methodName = method.getName();

                logger.debug("Method name : " + methodName);

                if(!methodName.startsWith(GET)) continue;

                currentMethod = method;
                object = currentMethod.invoke(wsObject);

                if(object.getClass().getName().equals("java.util.Date"))
                    object = Converter.DateToSQLDate((java.util.Date) object);

                if(currentMethod.getName().equals("getLevel")) {
                    object = new Level();
                    ((Level) object).setId(1);
                }

                if(!currentMethod.getName().equals("getId")) {
                    String setterName = SET + methodName.substring(3);
                    if (objEntity != null) {
                        currentMethod = objEntity.getClass().getMethod(setterName, object.getClass());
                        currentMethod.invoke(objEntity, object);
                    }
                    else logger.error("objEntity is null !");
                }
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return objEntity;
    }

    public static Object EntityToWS(Object entity){

        Object wsObject = null;
        ObjectFactory factory = new ObjectFactory();

        if(entity instanceof BookEntity) wsObject = factory.createBook();
        if(entity instanceof UserEntity) wsObject = factory.createUser();
        if(entity instanceof ReservedBook) wsObject = factory.createBookReserved();

        for(Method method : entity.getClass().getDeclaredMethods()){

            Object object = null;
            Method currentMethod;

            try{

                String methodName = method.getName();

                logger.info("------------------------------------");
                logger.debug("Method name : " + methodName);

                if(!methodName.startsWith(GET) && !methodName.startsWith(IS)) continue;

                currentMethod = method;

                logger.debug("Method call : " + currentMethod.getName() + "(from " + entity.getClass().getSimpleName() + ")");

                object = currentMethod.invoke(entity);

                if(object != null) {

                    logger.debug("Object package : " + object.getClass().getName());

                    boolean javaName = object.getClass().getName().startsWith(JAVA_PACKAGE);
                    boolean hibernateName = object.getClass().getName().startsWith(HIBERNATE_PACKAGE);

                    logger.debug("Object class = " + object.getClass().getSimpleName());

                    if(object.getClass().getSimpleName().equals("UserEntity"))
                        continue;

                    if(object.getClass().getSimpleName().equals("BookEntity"))
                        continue;

                    if(object.getClass().getSimpleName().equals("ReservedBookPK"))
                        continue;

                    if (!javaName && !hibernateName) {

                        logger.debug("Object class !Java & !Hibernate");

                        if (object.getClass().getSimpleName().equals("Level")) {
                            currentMethod = object.getClass().getMethod("getId");
                        }
                        else currentMethod = object.getClass().getMethod("getName");

                        object = currentMethod.invoke(object);

                    }
                    else {
                        if (object.getClass().getName().startsWith("java.sql")) {
                            Date date = (Date) object;
                            object = new java.util.Date(date.getTime());
                        }
                    }

                    if (!methodName.substring(3).equals("Password")) {

                        String setterName = SET;

                        if (methodName.startsWith(GET)) setterName += methodName.substring(3);
                        else if (methodName.startsWith(IS)) setterName += methodName.substring(2);

                        logger.info("Setter called = " + setterName);

                        if (wsObject != null) {

                            currentMethod = wsObject.getClass().getMethod(setterName, object.getClass());

                            currentMethod.invoke(wsObject, object);
                        } else logger.error("wsObject is null !");
                    }
                }
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                if (object != null)
                    logger.error("Object -> " + object.getClass().getName());
                else logger.error("Object -> null");
            }
        }

        return wsObject;
    }
}
