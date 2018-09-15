package com.github.rmcdouga.fitg.webapp.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

public class JsonUtil {
	private final static BigDecimal MAX_INT = new BigDecimal(Integer.MAX_VALUE);
	private final static BigDecimal MIN_INT = new BigDecimal(Integer.MIN_VALUE);
	private final static BigDecimal MAX_LONG = new BigDecimal(Long.MAX_VALUE);
	private final static BigDecimal MIN_LONG = new BigDecimal(Long.MIN_VALUE);
	private final static BigDecimal MAX_FLOAT = new BigDecimal(Float.MAX_VALUE);
	private final static BigDecimal MIN_FLOAT = new BigDecimal(Float.MIN_VALUE);
	private final static BigDecimal MAX_DOUBLE = new BigDecimal(Double.MAX_VALUE);
	private final static BigDecimal MIN_DOUBLE = new BigDecimal(Double.MIN_VALUE);
	
	
	public static Map<String, Object> JsonToMap(JsonObject Json) throws JsonException {
	    Map<String, Object> retMap = new HashMap<String, Object>();

	    if(Json != JsonObject.NULL) {
	        retMap = toMap(Json);
	    }
	    return retMap;
	}

	private static Map<String, Object> toMap(JsonObject object) throws JsonException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    for(String key : object.keySet()) {
	        JsonValue value = object.get(key);
	        map.put(key, convertJsonObjectToJavaObject(value));
	    }
	    return map;
	}

	private static List<Object> toList(JsonArray array) throws JsonException {
	    List<Object> list = new ArrayList<Object>();
		for(JsonValue value : array) {
	        list.add(convertJsonObjectToJavaObject(value));
	    }
	    return list;
	}
	
	private static Object convertJsonObjectToJavaObject(JsonValue value) {
		Object object = null;
		
        switch(value.getValueType()) {
        case OBJECT:
        	object = toMap((JsonObject) value);
        	break;
        case ARRAY:
        	object = toList((JsonArray) value);
            break;
        case NUMBER:
        	BigDecimal num = ((JsonNumber) value).bigDecimalValue();  // TODO: A lot more logic must go here
        	if (num.scale() == 0) {
        		if (isBetween(MIN_INT, num, MAX_INT)) {
        			object = new Integer(num.intValueExact());
        		} else if (isBetween(MIN_LONG, num, MAX_LONG)) {
        			object = new Long(num.longValueExact());
        		} else {
        			object = ((JsonNumber) value).bigIntegerValue();
        		}
        	 } else {
         		if (isBetween(MIN_FLOAT, num, MAX_FLOAT)) {
        			object = new Float(num.floatValue());
        		} else if (isBetween(MIN_DOUBLE, num, MAX_DOUBLE)) {
        			object = new Double(num.doubleValue());
        		} else {
        			object = num;
        		}
        	 }
        	break;
        case STRING:
        	object = ((JsonString) value).getString();
        	break;
        case TRUE:
        	object = Boolean.TRUE;
        	break;
        case FALSE:
        	object = Boolean.FALSE;
        	break;
        case NULL:
        	object = Optional.empty();
        	break;
        default:
        	throw new IllegalArgumentException("Unknown JsonValue type '" + value.getValueType().toString() + "'.");
        }
        return object;
	}
	
	public static <T extends Comparable<T>> boolean isBetween(T min, T value, T max) {
		return (value.compareTo(max) <= 0 && value.compareTo(min) >= 0) ? true : false;
	}
}
