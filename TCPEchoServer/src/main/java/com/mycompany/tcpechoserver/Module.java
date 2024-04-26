package com.mycompany.tcpechoserver;

/**
 *
 * @author noaca
 */
public class Module {
    String name;
    String className;
    public Module(String name, String className)
    {
    this.name = name;
    this.className=className;
    }
    public boolean hasClass(String c)
    {
    if(Module.this.name.equals(c))
    {
    return true;
    }else
    {
    return false;
    }
    }
}
