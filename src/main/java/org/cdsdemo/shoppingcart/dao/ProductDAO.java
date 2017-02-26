package org.cdsdemo.shoppingcart.dao;

import org.cdsdemo.shoppingcart.entity.Product;
import org.cdsdemo.shoppingcart.model.PaginationResult;
import org.cdsdemo.shoppingcart.model.ProductInfo;
 
public interface ProductDAO {    
    
    public Product findProduct(String code);
    
    public ProductInfo findProductInfo(String code) ;
  
    
    public PaginationResult<ProductInfo> queryProducts(int page,
                       int maxResult, int maxNavigationPage  );
    
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult,
                       int maxNavigationPage, String likeName);
 
    public void save(ProductInfo productInfo);
    
}