package com.study.orders;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
  int createOrder(OrdersDTO dto); // 갱신된 개수가 리턴되었지만 ,orderno 가 리턴되야한다. mybatis 를사용하면 편리해진다
  // OrderMapper 에서는 두개의 insert 를 처리하면서 AOP transaction 을 사용한다.

  void createDetail(OrderdetailDTO odto);

  List<OrdersDTO> list(Map map);

  int total(Map map);
}
