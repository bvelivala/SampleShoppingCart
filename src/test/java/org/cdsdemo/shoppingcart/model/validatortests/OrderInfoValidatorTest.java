package org.cdsdemo.shoppingcart.model.validatortests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.cdsdemo.shoppingcart.model.OrderInfo;
import org.junit.Test;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/** 
 *         Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
public class OrderInfoValidatorTest {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    public void shouldNotValidateWhenCustomerNameEmpty() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
                
        OrderInfo orderInfo = new OrderInfo("Order01", Calendar.getInstance().getTime(),
        							1, 10000.0, "", "#420", "test@gmail.com", "9876543210");
        
        orderInfo.setCustomerName("");
        Validator validator = createValidator();
        Set<ConstraintViolation<OrderInfo>> constraintViolations = validator.validate(orderInfo);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<OrderInfo> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("customerName");
        assertThat(violation.getMessage()).isEqualTo("may not be empty");	
    }
}
