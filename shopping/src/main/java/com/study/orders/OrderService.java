package com.study.orders;

public interface OrderService {

  void create(OrdersDTO dto)throws Exception; //AOP transaction Exception 이 발생할경우 롤백이되야한다.
  
}
