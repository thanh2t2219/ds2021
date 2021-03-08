package com.company.service_for_file_transfer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileSample {
    private String name;
    private String content;
    private Date dateCreation;

    public FileSample(String name, String content, Date dateCreation) {
        this.name = name;
        this.content = content;
        this.dateCreation = dateCreation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    private String convertDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY HH:mm");
        String dateSet = simpleDateFormat.format(dateCreation);
        return dateSet;
    }
    @Override
    public String toString() {
        return "Name : "+name+"\n"
                +"Content : "+content+"\n"
                +"Date : "+ convertDateToString(dateCreation);
    }
}
