package com.sharedLibrary.mapper;


import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ObjectEntityCheckutil {
	
	public static void copyNonNullProperties(Object sourceBean, Object targetBean) {
		   BeanUtils.copyProperties(sourceBean, targetBean, getNullOrEmptyProperties(sourceBean));
		}

	public static String[] getNullOrEmptyProperties (Object sourceBean) {
		   final BeanWrapper srcBean = new BeanWrapperImpl(sourceBean);
		   PropertyDescriptor[] propertyDescriptors = srcBean.getPropertyDescriptors();

		   Set<String> emptyOrNullProperties = new HashSet<String>();
		   for(PropertyDescriptor propertyDescriptor : propertyDescriptors) {
		   	/*System.out.println("PROPERTY TYPE ==== "+srcBean.getPropertyType(propertyDescriptor.getName()));
		   	System.out.println("Wrapped Instance ==== "+srcBean.getWrappedInstance());
		   	System.out.println("WRAPPED CLASS === "+srcBean.getWrappedClass().getName());*/
		   	Object srcBeanPropertyValue = srcBean.getPropertyValue(propertyDescriptor.getName());
		       if (srcBeanPropertyValue == null) emptyOrNullProperties.add(propertyDescriptor.getName());
		   }
		   String[] result = new String[emptyOrNullProperties.size()];
		   return emptyOrNullProperties.toArray(result);
		}

}
