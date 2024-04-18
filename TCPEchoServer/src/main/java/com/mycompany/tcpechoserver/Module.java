/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
