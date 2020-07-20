/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.librarymanagement.domain;

import java.time.LocalDate;

/**
 *
 * @author sire
 */
public class Issue {
    private int bookID;
    private int memberID;
    private LocalDate issueDate;
    private int renewCount;

    public Issue() {
    }

    public Issue(int bookID, int memberID) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.issueDate = LocalDate.now();
    }
    
    public Issue(int bookID, int memberID, LocalDate issueDate, int renewCount) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.issueDate = issueDate;
        this.renewCount = renewCount;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }
    
    
           
}
