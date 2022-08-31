package com.github.spencerk.ReimbursementAPI.entity;

public class AuthenticationResponse {
    private String jwt;
    private Employee employee;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt, Employee employee) {
        this.jwt = jwt;
        this.employee = employee;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return String.format("jwt: %s", jwt);
    }
}
