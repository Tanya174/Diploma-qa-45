package ru.netology.data;

public enum Status {
    APPROVED("APPROVED"),
    DECLINED("DECLINED");

    private String statusName;
    Status(String name){
        this.statusName = name;
    }
    public String getName(){
        return this.statusName;
    }
}