package com.relit.timemaangement.domain.semester;

import com.relit.timemaangement.util.Date;

public class Semester {
    private final int id;
    private String name;
    private String data;
    private Date startDate;
    private Date endDate;


    public Semester(int id, String name, String data, String startDate, String endDate){
        this(id, name, data, Date.parseString(startDate), Date.parseString(endDate));
    }

    public Semester(int id, String name, String data, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDateAsString(){
        return startDate.toString();
    }

    public String getEndDateAsString(){
        return endDate.toString();
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getData() {
        return data;
    }

    public int getID() {
        return id;
    }
}
