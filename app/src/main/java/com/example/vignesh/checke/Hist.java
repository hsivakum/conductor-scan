package com.example.vignesh.checke;

/**
 * Created by Hariharan Sivakumar on 2/28/2018.
 */

public class Hist {

    int Id;
    String from;
    String to;
    String amount;
    String name;
    String stop;
    String heads;
    String dates;
    String ticketid;
    String oldamount;
    String totalamount;



    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getOldamount()
    {
        return oldamount;
    }

    public void setOldamount(String oldamount2) {
        this.oldamount = oldamount2;
    }

    public String getName() {

        return name;
    }

    public String getTicketid()
    {
        return ticketid;
    }

    public String getTo()
    {
        return to;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount1)
    {
        this.amount= amount1;
    }

    public void setTo(String to1)
    {
        this.to = to1;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from1)
    {
        this.from = from1;
    }

    public void setTicketid(String ticketid1)
    {
        this.ticketid =  ticketid1;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getId() {

        return Id;
    }

    public void setId(int Id1) {

        this.Id = Id1;
    }



    public String getStop () {

        return stop;
    }

    public String getHeads() {

        return heads;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }


}
