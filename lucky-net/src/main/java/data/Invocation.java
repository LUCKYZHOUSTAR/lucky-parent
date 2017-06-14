package data;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:03 2017/6/12
 */
public interface Invocation {


    String getMethodName();

    Class<?>[] getParameterTypes();


    Object[] getArguments();




}
