package edu.zhku.json;

import java.lang.reflect.Type;
import java.util.Set;

import edu.zhku.bean.User;

public class TypeToken<T> {
	Class<? super T> rawType;
	Type type;

	TypeToken() {
		getSuperclassTypeParameter(getClass());
	}

	TypeToken(Type type) {
		System.out.println(type);
		getSuperclassTypeParameter(getClass());
	}

	void getSuperclassTypeParameter(Class<?> subclass) {
		System.out.println(subclass);
		Type superclass = subclass.getGenericSuperclass();
		if (superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		System.out.println(superclass);
	}

	public static <T> TypeToken<T> get(Class<T> type) {
		return new TypeToken<T>(type);
	}

	public void syHi() {
		System.out.println("Hello");
	}
	
	public static void main(String[] args) {
		System.out.println(TypeToken.get(new TypeToken<Set<User>>(){}.getClass()));
	}
}
