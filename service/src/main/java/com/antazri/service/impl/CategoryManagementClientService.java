package com.antazri.service.impl;

import com.antazri.generated.category.*;

import javax.xml.namespace.QName;
import java.net.URL;

public class CategoryManagementClientService extends CategoryManagementService implements CategoryPort {

    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        return getCategoryManagementPort().findById(parameters);
    }

    @Override
    public AddCategoryResponse addCategory(AddCategoryRequest parameters) throws ConvertException {
        return getCategoryManagementPort().addCategory(parameters);
    }

    @Override
    public UpdateCategoryResponse updateCategory(UpdateCategoryRequest parameters) throws ConvertException {
        return getCategoryManagementPort().updateCategory(parameters);
    }

    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        return getCategoryManagementPort().findAll(parameters);
    }

    @Override
    public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest parameters) throws ConvertException {
        return getCategoryManagementPort().deleteCategory(parameters);
    }
}
