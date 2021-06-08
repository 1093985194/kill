package com.oyzy.kill.vo;

import com.oyzy.kill.utils.ValidatorUtil;
import com.oyzy.kill.validator.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码校验规则
 * 校验类需要实现ConstraintValidator接口。
 * 接口使用了泛型，需要指定两个参数，第一个自定义注解类，第二个为需要校验的数据类型。
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

	private boolean required = false;

	/**
	 * 实现接口后要override两个方法，分别为initialize方法和isValid方法。
	 * 其中initialize为初始化方法，可以在里面做一些初始化操作，
	 * isValid方法就是我们最终需要的校验方法了。可以在该方法中实现具体的校验步骤。
	 * @param constraintAnnotation
	 */
	@Override
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (required){
			return ValidatorUtil.isMobile(value);
		}else {
			if (StringUtils.isEmpty(value)){
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}
}
