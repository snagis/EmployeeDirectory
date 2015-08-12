package com.hspring.model;

/**
 * Created by snagis on 8/2/15.
 */
public class SearchCriteria {
    public String firstName;
    public String lastName;
    public String email;
    public String searchTerm;


    public void setSearchTerm(String searchTerm){
	this.searchTerm = searchTerm;
    }

    public String getSearchTerm(){
	return this.searchTerm;
    }

    public boolean searchByLastName(){
       return lastName != null && ! "".equals(lastName);
    }

    public boolean isValid(){
       return getSearchConditionCount() > 0 || (searchTerm != null && !searchTerm.equals(""));
    }

    private int getSearchConditionCount(){
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
        return searchCriteriaCount;
    }

    public boolean isMultipleSearchCriteria(){
        return getSearchConditionCount() > 1;
    }

    public boolean searchByFirstName(){
        return firstName != null && ! "".equals(firstName);
    }

    public boolean searchByEmail(){
        return email != null && ! "".equals(email);
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
