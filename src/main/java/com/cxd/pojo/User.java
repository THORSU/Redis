package com.cxd.pojo;

import java.io.Serializable;


public class User implements Serializable {
	
	private static final long serialVersionUID = 522889572773714584L;
	
	public static final String OBJECT_KEY = "USER";  
	private String sid;
	private String sname;
	private String spassword;
    private String smobile;
    public User() {}

    public User(String id,String name,String password) {
        this.sid = id;
        this.sname = name;
        this.spassword= password;
    }

    public String getSmobile() {
        return smobile;
    }

    public void setSmobile(String smobile) {
        this.smobile = smobile;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSpassword() {
        return spassword;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    public String toString() {
	        return "User [sid=" + spassword + ", sname=" + spassword + ",spassword="+spassword+",smobile="+smobile+"]";
	    }

	    public String getKey() {
	        return getSid();
	    }  
	  
	    public String getObjectKey() {  
	        return OBJECT_KEY;  
	    }  
}
