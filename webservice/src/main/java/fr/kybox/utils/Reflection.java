package fr.kybox.utils;

import fr.kybox.entities.BookEntity;
import fr.kybox.gencode.Book;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Kybox
 * @version 1.0
 */
public class Reflection {

    public static Book Book(BookEntity bookEntity){

        Book book = new Book();

        for(Method method : bookEntity.getClass().getDeclaredMethods()){

            try{

                String methodName = method.getName();

                if(methodName.startsWith("get") || methodName.startsWith("is")){

                    Method currentMethod = method;
                    Object object = currentMethod.invoke(bookEntity);

                    String tempName = methodName.substring(3);
                    if(tempName.equals("Author") || tempName.equals("Publisher") || tempName.equals("Genre")){

                        currentMethod = object.getClass().getMethod("getName");
                        object = currentMethod.invoke(object);
                    }

                    String setterName;
                    if(methodName.startsWith("get"))
                        setterName = "set" + methodName.substring(3);
                    else setterName = "set" + methodName.substring(2);

                    if(!tempName.equals("Publisherdate")) {
                        currentMethod = book.getClass().getMethod(setterName, object.getClass());
                        currentMethod.invoke(book, object);
                    }
                }
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return book;
    }
}
