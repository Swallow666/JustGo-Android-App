package com.example.liyuan.justgo.Model;


public class comment {
    private int commmentid;
    private String comment;
    private int planid;

    public void comment(int commentid)
    {this.commmentid = commentid;}

    public int getCommmentid()
    {return commmentid;}
    public void setCommmentid(int commmentid)
    {this.commmentid = commmentid;}

    public String getComment()
    {return comment;}
    public void setComment(String comment)
    {this.comment = comment;}

    public int getPlanid()
    {return planid;}
    public void setPlanid(int planid)
    {this.planid = planid;}

}
