package com.how2java.tmall.service;

import com.how2java.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {

    List<PropertyValue> list(int pid);

    void update(PropertyValue propertyValue);

    void setProperty(PropertyValue propertyValue);

    void setProperty(List<PropertyValue> propertyValues);
}
