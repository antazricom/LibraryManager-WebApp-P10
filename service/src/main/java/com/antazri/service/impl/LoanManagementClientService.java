package com.antazri.service.impl;

import com.antazri.generated.loan.*;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import javax.naming.ConfigurationException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LoanManagementClientService extends LoanManagementService implements LoanPort {

    @Override
    public DeleteLoanResponse deleteLoan(DeleteLoanRequest parameters) throws ConvertException {
        return getLoanManagementPort().deleteLoan(parameters);
    }

    @Override
    public IsEndingResponse isEnding(IsEndingRequest parameters) throws ConvertException {
        return getLoanManagementPort().isEnding(parameters);
    }

    @Override
    public ReturnLoanResponse returnLoan(ReturnLoanRequest parameters) throws ReturnException, ConvertException {
        return getLoanManagementPort().returnLoan(parameters);
    }

    @Override
    public UpdateLoanResponse updateLoan(UpdateLoanRequest parameters) throws ConvertException {
        return getLoanManagementPort().updateLoan(parameters);
    }

    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        return getLoanManagementPort().findById(parameters);
    }

    @Override
    public AddLoanResponse addLoan(AddLoanRequest parameters) throws ConvertException {
        return getLoanManagementPort().addLoan(parameters);
    }

    @Override
    public FindByBookResponse findByBook(FindByBookRequest parameters) throws ConvertException {
        return getLoanManagementPort().findByBook(parameters);
    }

    @Override
    public ExtendLoanResponse extendLoan(ExtendLoanRequest parameters) throws ExtendException, ConvertException {
        return getLoanManagementPort().extendLoan(parameters);
    }

    @Override
    public FindByMemberResponse findByMember(FindByMemberRequest parameters) throws ConvertException {
        return getLoanManagementPort().findByMember(parameters);
    }

    @Override
    public GetStatusResponse getStatus(GetStatusRequest parameters) throws ConvertException {
        return getLoanManagementPort().getStatus(parameters);
    }

    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        return getLoanManagementPort().findAll(parameters);
    }
}
