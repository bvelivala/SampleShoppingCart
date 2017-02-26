package org.cdsdemo.shoppingcart.dao;

import java.util.List;

import org.cdsdemo.shoppingcart.model.CartInfo;
import org.cdsdemo.shoppingcart.model.OrderDetailInfo;
import org.cdsdemo.shoppingcart.model.OrderInfo;
import org.cdsdemo.shoppingcart.model.PaginationResult;
 
public interface OrderDAO {
 
    public void saveOrder(CartInfo cartInfo);
 
    public PaginationResult<OrderInfo> listOrderInfo(int page,
            int maxResult, int maxNavigationPage);
    
    public OrderInfo getOrderInfo(String orderId);
    
    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);
 
}
