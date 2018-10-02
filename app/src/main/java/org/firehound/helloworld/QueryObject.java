package org.firehound.helloworld;

import java.sql.Timestamp;

public class QueryObject{
    public String userId;
    public Timestamp timestamp;
    public boolean[] symptomList;



    public QueryObject(String userId,  Timestamp timestamp, boolean[] symptomList) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.symptomList = symptomList;
    }
}
