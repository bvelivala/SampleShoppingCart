package org.cdsdemo.shoppingcart.model.validatortests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.cdsdemo.shoppingcart.model.ProductInfo;
import org.junit.Test;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/** 
 *         Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
public class ProductInfoValidatorTest {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    public void shouldNotValidateWhenProductNameEmpty() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
                
        ProductInfo productInfo = new ProductInfo();        
        productInfo.setName("");
        
        Validator validator = createValidator();
        Set<ConstraintViolation<ProductInfo>> constraintViolations = validator.validate(productInfo);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<ProductInfo> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("may not be empty");	
    }
}
