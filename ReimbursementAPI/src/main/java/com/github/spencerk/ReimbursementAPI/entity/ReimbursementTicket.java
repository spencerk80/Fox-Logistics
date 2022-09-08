package com.github.spencerk.ReimbursementAPI.entity;

import com.github.spencerk.ReimbursementAPI.enums.ReimbursementCategory;
import com.github.spencerk.ReimbursementAPI.enums.ReimbursementStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ReimbursementTicket {
    private String                  id;
    private String                  employeeId;
    private float                   amount;
    private ReimbursementCategory   category;
    private String                  userComment;
    private ReimbursementStatus     status;
    private LocalDateTime           timeStamp;

    public ReimbursementTicket() {
        this.id = UUID.randomUUID().toString();
        this.timeStamp = LocalDateTime.now();
    }

    public ReimbursementTicket(
            String employeeID, float amount, ReimbursementCategory category, String userComment, ReimbursementStatus status
    ) {
        this.id = UUID.randomUUID().toString();
        this.employeeId = employeeID;
        this.amount = amount;
        this.category = category;
        this.userComment = userComment;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }

    public ReimbursementTicket(
            String id, String employeeID, float amount, ReimbursementCategory category, String userComment,
            ReimbursementStatus status
    ) {
        this.id = id;
        this.employeeId = employeeID;
        this.amount = amount;
        this.category = category;
        this.userComment = userComment;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public ReimbursementStatus getStatus() {
        return status;
    }

    public void setStatus(ReimbursementStatus status) {
        this.status = status;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean validate() {
        return  ! "".equals(this.id) &&
                ! "".equals(this.employeeId) &&
                this.amount != 0;
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
                "{\n\tid: %s,\n\temployeeID: %s,\n\tamount: $%,.2f,\n\tstatus: %s,\n\tcategory: %s,\n\t" +
                        "userComment: %s,\n\ttimeStamp: %s\n}",
                this.id,
                this.employeeId,
                this.amount,
                this.status,
                this.category,
                this.userComment,
                this.timeStamp
        );
    }
}
