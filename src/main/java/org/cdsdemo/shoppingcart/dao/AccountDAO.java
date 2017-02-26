package org.cdsdemo.shoppingcart.dao;

import org.cdsdemo.shoppingcart.entity.Account;

public interface AccountDAO { 
    
    public Account findAccount(String userName );
    
}
