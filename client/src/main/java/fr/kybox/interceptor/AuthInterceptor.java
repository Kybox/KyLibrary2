package fr.kybox.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import static fr.kybox.utils.ValueTypes.*;

/**
 * @author Kybox
 * @version 1.0
 */
public class AuthInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        String result = null;

        String[] userLevels = {LEVEL_ADMIN, LEVEL_MANAGER, LEVEL_CLIENT};

        for(String level : userLevels){
            if(invocation.getInvocationContext().getSession().get(level) != null){
                result = invocation.invoke();
                break;
            }
        }

        if(result == null)
            result = REDIRECT_403;

        return result;
    }
}
