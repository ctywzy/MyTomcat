package niotomcat.tomcatobj;

import java.io.Serializable;


public class Cookie implements Serializable {

    private static final long serialVersionUID = 5740244721650337284L;
    /**
     * cookie名称
     */
    private String name;

    /**
     * cookie值
     */
    private String value;
}
