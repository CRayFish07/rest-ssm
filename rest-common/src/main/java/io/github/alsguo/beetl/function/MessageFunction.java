package io.github.alsguo.beetl.function;

import java.lang.reflect.Constructor;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.support.RequestContext;

public class MessageFunction implements Function{

	public Object call(Object[] paras, Context ctx) {

		return null;
	}
	
	public Object validate(Context ctx) throws Exception{
		Class classType = Class.forName("org.springframework.web.servlet.support.RequestContext");
		Class reqClass = Class.forName("javax.servlet.http.HttpServletRequest");
		Constructor con = classType.getConstructor(reqClass);
		RequestContext requestContext = (RequestContext) con.newInstance(ctx);
		Errors errors = requestContext.getErrors("");
		errors.getFieldErrorCount();
		errors.getFieldErrors();
		
		FieldError fieldError = errors.getFieldError("");
		fieldError.getField();		
		fieldError.getCode();
		fieldError.getDefaultMessage();
		return fieldError;
	}
}
