package com.antazri.service.impl;

import com.antazri.generated.member.*;

import javax.xml.namespace.QName;
import java.net.URL;

public class MemberManagementClientService extends MemberManagementService implements MemberPort {

    @Override
    public AddMemberResponse addMember(AddMemberRequest parameters) throws ConvertException {
        return getMemberManagementPort().addMember(parameters);
    }

    @Override
    public UpdateMemberResponse updateMember(UpdateMemberRequest parameters) throws ConvertException {
        return getMemberManagementPort().updateMember(parameters);
    }

    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        return getMemberManagementPort().findById(parameters);
    }

    @Override
    public DeleteMemberResponse deleteMember(DeleteMemberRequest parameters) throws ConvertException {
        return getMemberManagementPort().deleteMember(parameters);
    }

    @Override
    public FindByLoginResponse findByLogin(FindByLoginRequest parameters) throws ConvertException, BadLoginException {
        return getMemberManagementPort().findByLogin(parameters);
    }

    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        return getMemberManagementPort().findAll(parameters);
    }

//    public boolean checkConfirmedPassword(String pPassword, String pConfirmedPassword) {
//        if (pPassword.equals(pConfirmedPassword)) {
//            return true;
//        }
//
//        return false;
//    }
}
