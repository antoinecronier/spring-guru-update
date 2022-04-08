package com.springframework.guru.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyPageableRequest {
    private Date start;
    private Date end;
    private List<String> partenaires = new ArrayList<>();
    private int limit;
    private int offset;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<String> getPartenaires() {
        return partenaires;
    }

    public void setPartenaires(List<String> partenaires) {
        this.partenaires = partenaires;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
