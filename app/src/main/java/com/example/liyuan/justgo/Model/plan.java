package com.example.liyuan.justgo.Model;


public class plan {
    private int planid;
    private String plan_name;
    private Double budget;
    private String description;
    private int userid;

    public plan(){}

    public int getPlanid()
    {return planid;}
    public void setPlanid(int planid)
    {this.planid = planid;}

    public String getPlan_name()
    {return plan_name;}
    public void setPlan_name(String plan_name)
    {this.plan_name = plan_name;}

    public Double getBudget()
    {return budget;}
    public void setBudget(Double budget)
    {this.budget = budget;}

    public String getDescription()
    {return description;}
    public void setDescription(String description)
    {this.description = description;}

    public int getUserid()
    {return userid;}
    public void setUserid(int userid)
    {this.userid = userid;}
}
