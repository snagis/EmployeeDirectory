package com.hspring.model;

/**
 * Created by snagis on 8/2/15.
 */
public class SearchCriteria {
    public String firstName;
    public String lastName;
    public String email;

    public boolean searchByLastName(){
       return ! "".equals(lastName);
    }

    public boolean isMultipleSearchCriteria(){
        int searchCriteriaCount = 0;
        if(searchByFirstName()) {
            searchCriteriaCount++;
        }
        if(searchByLastName()) {
            searchCriteriaCount++;
        }
        if(searchByEmail()) {
            searchCriteriaCount++;
        }
        return searchCriteriaCount > 1;
    }

    public boolean searchByFirstName(){
        return ! "".equals(firstName);
    }

    public boolean searchByEmail(){
        return ! "".equals(email);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
