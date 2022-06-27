package com.study.orders;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

  int createOrder(OrdersDTO dto); // orders테이블에 insert하는거 => orderno가 리턴되야함

  void createDetail(OrderdetailDTO odto);// order detail에 insert

  List<OrdersDTO> list(Map map);

  int total(Map map);

}
