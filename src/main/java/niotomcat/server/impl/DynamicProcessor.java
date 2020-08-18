package niotomcat.server.impl;

import niotomcat.server.Processor;
import niotomcat.tomcatobj.HttpServletRequest;
import niotomcat.tomcatobj.HttpServletResponse;

public class DynamicProcessor implements Processor {

    @Override
    public void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        //1、假设请求的地址为  /hello.action  ->对应的是hello 这个servlet 类
        //从request中取出getRequestURI
        String url = httpServletRequest.getUrl();

        //2、去掉 / 及action 两个部分 ，得到hello  ，就是servlet类名

        String servletName;
    }
}
