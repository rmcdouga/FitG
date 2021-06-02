package com.rogers.rmcdouga.fitg.svgviewer;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

public class GenerateEnum {


	@Test
	void generatePlanetEnum() throws Exception {
		Class<BaseGamePlanet> basedOnClass = BaseGamePlanet.class;
		final String targetClassPackageName = "com.rogers.rmcdouga.fitg.svgviewer";
		final String targetClassName = "Planet";
		final String memberVariableName = "bgPlanet";

		generateClass(basedOnClass, targetClassPackageName, targetClassName, memberVariableName);

	}

	@Test
	void generateStarSystemEnum() throws Exception {
		Class<BaseGameStarSystem> basedOnClass = BaseGameStarSystem.class;
		final String targetClassPackageName = "com.rogers.rmcdouga.fitg.svgviewer";
		final String targetClassName = "StarSystem";
		final String memberVariableName = "bgStarSystem";

		generateClass(basedOnClass, targetClassPackageName, targetClassName, memberVariableName);

	}

	private static <T extends Enum<T>> void generateClass(Class<T> basedOnClass, final String targetClassPackageName,
			final String targetClassName, final String memberVariableName) throws IOException {
		Builder builder = TypeSpec.enumBuilder(targetClassName)
				.addModifiers(Modifier.PUBLIC);

		Stream.of(basedOnClass.getEnumConstants())
			.forEach(n->builder
					.addEnumConstant(n.toString(), TypeSpec.anonymousClassBuilder("$L", ClassName.get(basedOnClass).simpleName()+"."+n).build())
					);

		
		MethodSpec stream = MethodSpec.methodBuilder("stream")
				.returns(ParameterizedTypeName.get(ClassName.get(Stream.class), ClassName.get(targetClassPackageName, targetClassName)))
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addStatement("return Stream.of(values())")
				.build();
		
		MethodSpec streamFilter = MethodSpec.methodBuilder("stream")
				.addParameter(ParameterizedTypeName.get(ClassName.get(Predicate.class), ClassName.get(targetClassPackageName, targetClassName)), "predicate")
				.returns(ParameterizedTypeName.get(ClassName.get(Stream.class), ClassName.get(targetClassPackageName, targetClassName)))
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addStatement("return stream().filter(predicate)")
				.build();
		
		MethodSpec ctr 	= MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PRIVATE)
				.addParameter(basedOnClass, memberVariableName)
				.addStatement("this.$N = $N", memberVariableName, memberVariableName)
				.build();
		
		TypeSpec planetEnumClass = builder
				.addField(basedOnClass, memberVariableName, Modifier.PRIVATE, Modifier.FINAL)
				.addMethod(ctr)
				.addMethod(stream)
				.addMethod(streamFilter)
				.build();
		
		JavaFile javaFile = JavaFile.builder(targetClassPackageName, planetEnumClass)
			    .build();

		javaFile.writeTo(System.out);
	}
	
	@Disabled
	@Test
	void generateHelloWorld() throws Exception {
		MethodSpec main = MethodSpec.methodBuilder("main")
			    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
			    .returns(void.class)
			    .addParameter(String[].class, "args")
			    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
			    .build();

		TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
			    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
			    .addMethod(main)
			    .build();

		JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
			    .build();

			javaFile.writeTo(System.out);
	}
}
