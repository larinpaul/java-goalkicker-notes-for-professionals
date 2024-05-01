//# Chapter 51: The java.util.Objects Class

//# Section 51.1: Basic use for object null check

// For null check in method
Object nullableObject = methodReturnObject();
if (Objects.isNull(nullableObject)) {
	return;
}

// For not null check in method
Object nullableObject = methodReturnObject();
if (Objects.notNull(nullableObject)) {
	return;
}

//# Section 51.2: Objects.notNull() method reference use in stream api

// In the old fashion way for collection null check
List<Object> someObject = methodGetList();
for (Object obj : someObjects) {
	if (obj == null) {
		continue;
	}
	doSomething(obj);
}

// With the Objects.nonNull method and Java8 Stream API, we can do the above in this way:
List<Object> someObjects = methodGetList();
someObjects.stream()
			.filter(Objects::nonNull)
			.forEach(this::doSomething);
			