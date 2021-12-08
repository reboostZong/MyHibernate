package com.zcf.pojo;

public class IDCard {
    private Long id;
    private String code;
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "IDCard{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
