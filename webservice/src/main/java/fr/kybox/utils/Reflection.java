package fr.kybox.utils;

import fr.kybox.entities.BookEntity;
import fr.kybox.gencode.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;

/**
 * @author Kybox
 * @version 1.0
 */
public class Reflection {

    private final static Logger logger = LogManager.getLogger(Reflection.class);

    public static Book Book(BookEntity bookEntity){

        Book book = new Book();

        for(Method method : bookEntity.getClass().getDeclaredMethods()){

            Object object;
            Method currentMethod;

            try{

                String methodName = method.getName();

                if(methodName.startsWith("get")){

                    currentMethod = method;
                    object = currentMethod.invoke(bookEntity);

                    boolean javaName = object.getClass().getName().startsWith("java");
                    boolean hibernateName = object.getClass().getName().startsWith("org.hibernate");

                    if(!javaName && !hibernateName){

                        if(object.getClass().getName().equals("Level"))
                            currentMethod = object.getClass().getMethod("getId");
                        else
                            currentMethod = object.getClass().getMethod("getName");

                        object = currentMethod.invoke(object);
                    }
                    else{
                        if(object.getClass().getName().startsWith("java.sql")) {
                            Date date = (Date) object;
                            object = new java.util.Date(date.getTime());
                        }
                    }

                    String setterName = "set" + methodName.substring(3);
                    currentMethod = book.getClass().getMethod(setterName, object.getClass());
                    currentMethod.invoke(book, object);
                }
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error(e.getMessage());
            }
        }

        return book;
    }
}
