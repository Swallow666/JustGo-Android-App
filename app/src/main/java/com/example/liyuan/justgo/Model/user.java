package com.example.liyuan.justgo.Model;


public class user {
    private int userid;
    private String first_name;
    private String last_name;
    private String country;
    private String email;
    private String password;

    public user(){}

    public int getUserid()
    {return userid;}
    public void setUserid(int userid)
    {this.userid = userid;}

    public String getFirst_name()
    {return first_name;}
    public void setFirst_name(String first_name)
    {this.first_name = first_name;}

    public String getLast_name()
    {return last_name;}
    public void setLast_name(String last_name)
    {this.last_name = last_name;}

    public String getCountry()
    {return country;}
    public void setCountry(String country)
    {this.country = country;}

    public String getEmail()
    {return email;}
    public void setEmail(String email)
    {this.email = email;}

    public String getPassword()
    {return password;}
    public void setPassword(String password)
    {this.password = password;}

}
