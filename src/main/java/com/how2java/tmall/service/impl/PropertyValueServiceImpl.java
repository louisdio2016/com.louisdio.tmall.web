package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.PropertyValueMapper;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.PropertyValueExample;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    private PropertyValueMapper propertyValueMapper;
    @Autowired
    private PropertyService propertyService;

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id DESC");
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        setProperty(propertyValues);
        return propertyValues;
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    public void setProperty(List<PropertyValue> propertyValues){
        if (propertyValues.isEmpty() || propertyValues.size() == 0)
            return ;
        for (PropertyValue propertyValue:propertyValues){
            setProperty(propertyValue);
        }
    }

    public void setProperty(PropertyValue propertyValue){
        if (propertyValue == null)
            return ;
        propertyValue.setProperty(propertyService.get(propertyValue.getPtid()));
    }
}
