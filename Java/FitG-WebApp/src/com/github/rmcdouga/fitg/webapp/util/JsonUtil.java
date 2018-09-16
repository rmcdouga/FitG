package com.github.rmcdouga.fitg.webapp.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
	    return Json != JsonObject.NULL ? toMap(Json) : new HashMap<String, Object>();
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
	
	public static JsonObject MapToJson(Map<String, Object> map, String rootElement) throws JsonException {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		objectBuilder.add(rootElement, toJson(map));
		return objectBuilder.build();
	}

	private static JsonObjectBuilder toJson(Map<String, Object> map) {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		for (Entry<String, Object> entry: map.entrySet()) {
			Object value = entry.getValue();
			String key = entry.getKey();
			if (value == null) {
				objectBuilder.addNull(key);
			} else if (value instanceof Map) {
				objectBuilder.add(key, toJson((Map<String, Object>)value));
			} else if (value instanceof List) {
				objectBuilder.add(key, toJson((List<Object>)value));
			} else if (value instanceof String) {
				objectBuilder.add(key, (String)value);
			} else if (value instanceof Integer) {
				objectBuilder.add(key, ((Integer)value).intValue());
			} else if (value instanceof Boolean) {
				objectBuilder.add(key, ((Boolean)value).booleanValue());
			} else if (value instanceof Double) {
				objectBuilder.add(key, ((Double)value).doubleValue());
			} else if (value instanceof Long) {
				objectBuilder.add(key, ((Long)value).longValue());
			} else if (value instanceof BigDecimal) {
				objectBuilder.add(key, ((BigDecimal)value));
			} else if (value instanceof BigInteger) {
				objectBuilder.add(key, ((BigInteger)value));
			} else {
	        	throw new IllegalArgumentException("Unknown object primitive type '" + value.getClass().getName() + "'.");
			}
		}
		return objectBuilder;
	}

	private static JsonArrayBuilder toJson(List<Object> list) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (Object object : list) {
			if (object == null) {
				arrayBuilder.addNull();
			} else if (object instanceof Map) {
				arrayBuilder.add(toJson((Map<String,Object>)object));
			} else if (object instanceof List) {
				arrayBuilder.add(toJson((List<Object>)object));
			} else if (object instanceof String) {
				arrayBuilder.add((String)object);
			} else if (object instanceof Integer) {
				arrayBuilder.add(((Integer)object).intValue());
			} else if (object instanceof Boolean) {
				arrayBuilder.add(((Boolean)object).booleanValue());
			} else if (object instanceof Double) {
				arrayBuilder.add(((Double)object).doubleValue());
			} else if (object instanceof Long) {
				arrayBuilder.add(((Long)object).longValue());
			} else if (object instanceof BigDecimal) {
				arrayBuilder.add(((BigDecimal)object));
			} else if (object instanceof BigInteger) {
				arrayBuilder.add(((BigInteger)object));
			} else {
	        	throw new IllegalArgumentException("Unknown object primitive type '" + object.getClass().getName() + "'.");
			}
			
		}
		return arrayBuilder;
	}
}
