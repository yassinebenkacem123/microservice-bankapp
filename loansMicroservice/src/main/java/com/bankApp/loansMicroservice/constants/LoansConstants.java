package com.bankApp.loansMicroservice.constants;

public final class LoansConstants {

    private LoansConstants(){

    }

    public static final String HOME_LOAN = "Home Loan";
    public static final int NEW_LOAN_LIMIT = 1_00_000;

    public static final String STATUS_201 = "201";
    public static  final String MESSAGE_201 = "Loan created successfully";

    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Request processed successfully";

    public static final String STATUS_417 = "407";
    public static String MESSAGE_417 = "Update operation Failed. Please try again or Contact DEV team";

    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "Internal server error";



}