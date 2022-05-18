package com.yeocak.proje3.model;

public class User {
    private String name;
    private String phone;
    private String tc;
    private String address;
    private String email;
    private String password;
    private Role role;
    private String rep_tc;


    public User(String name, String phone, String tc, String address, String email, String password, Role role, String rep_tc) {
        this.name = name;
        this.phone = phone;
        this.tc = tc;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
        this.rep_tc = rep_tc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRep_tc() {
        return rep_tc;
    }

    public void setRep_tc(String rep_tc) {
        this.rep_tc = rep_tc;
    }
}
