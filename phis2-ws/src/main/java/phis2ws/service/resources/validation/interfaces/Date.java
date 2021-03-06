//******************************************************************************
//                                       Date.java
// SILEX-PHIS
// Copyright © INRA 2018
// Creation date: 21, Jun 2018
// Contact: arnaud.charleroy@inra.fr, morgane.vidal@inra.fr, anne.tireau@inra.fr, 
// pascal.neveu@inra.fr
//******************************************************************************
package phis2ws.service.resources.validation.interfaces;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import phis2ws.service.configuration.DateFormat;
import phis2ws.service.resources.validation.validators.DateFormatValidator;

/**
 * Used to specify if a string has a valid date format.
 * @see DateFormat
 * @see DateFormatValidator
 * @author Arnaud Charleroy <arnaud.charleroy@inra.fr>, Morgane Vidal <morgane.vidal@inra.fr>
 */
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = DateFormatValidator.class)
public @interface Date {
    String message() default "is not a valid date. Excepted format : {value}";
 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
    
    DateFormat[] value() default DateFormat.YMDHMSZ;
}