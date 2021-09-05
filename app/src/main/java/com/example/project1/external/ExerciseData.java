package com.example.project1.external;

import java.util.List;

public class ExerciseData {

    private int count;
    private String next;
    private String previous;
    private List<ExerciseDataInfo> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<ExerciseDataInfo> getResults() {
        return results;
    }

    public void setResults(List<ExerciseDataInfo> results) {
        this.results = results;
    }
}
