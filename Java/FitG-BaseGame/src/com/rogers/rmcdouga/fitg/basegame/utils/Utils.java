package com.rogers.rmcdouga.fitg.basegame.utils;

import java.util.function.Consumer;

public interface Utils {

	public static <T> T tap(T object, Consumer<T> consumer) {
		consumer.accept(object);
		return object;
	}
}
