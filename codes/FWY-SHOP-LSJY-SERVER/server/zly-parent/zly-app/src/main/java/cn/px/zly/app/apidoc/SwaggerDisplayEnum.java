package cn.px.zly.app.apidoc;

import lombok.ToString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerDisplayEnum {

    String num() default "num";

    String name() default "name";
}
