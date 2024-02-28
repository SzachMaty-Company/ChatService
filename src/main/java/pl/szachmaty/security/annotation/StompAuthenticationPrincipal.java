package pl.szachmaty.security.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface StompAuthenticationPrincipal {

}
