package com.github.spencerk.ReimbursementAPI.Entity;

import com.github.spencerk.ReimbursementAPI.enums.ReimbursementCategory;

import java.util.Objects;
import java.util.UUID;

public class ReimbursementTicket {
    private UUID                    id,
                                    employeeID;
    private float                   amount;
    private ReimbursementCategory   category;
    private String                  userComment;

    public ReimbursementTicket() {
        this.id = UUID.randomUUID();
    }

    public ReimbursementTicket(UUID employeeID, float amount, ReimbursementCategory category, String userComment) {
        this.id = UUID.randomUUID();
        this.employeeID = employeeID;
        this.amount = amount;
        this.category = category;
        this.userComment = userComment;
    }

    public ReimbursementTicket(UUID id, UUID employeeID, float amount, ReimbursementCategory category, String userComment) {
        this.id = id;
        this.employeeID = employeeID;
        this.amount = amount;
        this.category = category;
        this.userComment = userComment;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(UUID employeeID) {
        this.employeeID = employeeID;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ReimbursementCategory getCategory() {
        return category;
    }

    public void setCategory(ReimbursementCategory category) {
        this.category = category;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    @Override
    public boolean equals(Object o) {
        ReimbursementTicket that;

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        that = (ReimbursementTicket) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "{\n\tid: %s,\n\temployeeID: %s,\n\tamount: $%,.2f\n\tcategory: %s\n\t,userComment: %s\n}",
                this.id,
                this.employeeID,
                this.amount,
                this.category,
                this.userComment
        );
    }
}
