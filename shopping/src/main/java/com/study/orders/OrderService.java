package com.study.orders;

import java.util.List;
import java.util.Map;

public interface OrderService {

  void create(OrdersDTO dto)throws Exception; //AOP transaction Exception 이 발생할경우 롤백이되야한다.
  
  List<OrdersDTO> list(Map map);

  int total(Map map);
}
