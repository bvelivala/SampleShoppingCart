package org.cdsdemo.shoppingcart.model.validatortests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.cdsdemo.shoppingcart.model.CartInfo;
import org.junit.Test;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/** 
 *         Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
public class CartInfoValidatorTest {
     //Create validator
    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    public void orderNumTooLow() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
                
        CartInfo cartInfo = new CartInfo();
        cartInfo.setOrderNum(0);
        
        Validator validator = createValidator();
        Set<ConstraintViolation<CartInfo>> constraintViolations = validator.validate(cartInfo);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<CartInfo> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("orderNum");
        assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 1");	
    }
}
