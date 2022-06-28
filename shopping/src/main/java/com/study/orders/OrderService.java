package com.study.orders;

import java.util.List;
import java.util.Map;

public interface OrderService {

  void create(OrdersDTO dto) throws Exception; // AOP. 오류가 나면 롤백이 될 수 있게 throws

  List<OrdersDTO> list(Map map);

  int total(Map map);

  int updateState(Map map);

}
