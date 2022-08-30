package com.github.spencerk.ReimbursementAPI.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.Objects;
import java.util.UUID;

public class Employee {
    @Id
    private String    id;
    private String  username,
                    fname,
                    lname,
                    email,
                    phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String  password;

    public Employee() {
        this.id = UUID.randomUUID().toString();
    }

    public Employee(String username, String fname, String lname, String email, String phone, String password) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Employee(String id, String username, String fname, String lname, String email, String phone, String password) {
        this.id = id;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validate() {
        return  ! "".equals(this.id) &&
                ! "".equals(this.lname) &&
                ! "".equals(this.fname) &&
                ! "".equals(this.email) &&
                this.email.matches("[a-zA-Z\\d._-]+@.+\\..+") &&
                ! "".equals(this.phone) &&
                ! "".equals(this.password);
    }

    @Override
    public boolean equals(Object o) {
        Employee employee;

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        employee = (Employee) o;

        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "{\n\tid: %s,\n\tusername: %s,\n\tfname: %s,\n\tlname: %s,\n\temail: %s,\n\tphone: %s\n}",
                this.id,
                this.username,
                this.fname,
                this.lname,
                this.email,
                this.phone
        );
    }
}
