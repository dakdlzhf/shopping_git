package com.study.orders;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.study.orders.OrderServiceImpl")
public class OrderServiceImpl implements OrderService {
  
  @Autowired
  private OrderMapper mapper;
  
  @Override
  public void create(OrdersDTO dto) throws Exception {
    
    mapper.createOrder(dto); //dto에 orderno가 들어온다.(insert할때 들어옴)
    int orderno = dto.getOrderno();
    System.out.println(orderno);//확인용
    
    List<OrderdetailDTO> list = dto.getList(); //컨트롤러에서 list.add한거 꺼내오는거
    
    for(int i=0; i<list.size();i++) {
      OrderdetailDTO odto = list.get(i);
      odto.setOrderno(orderno);
      System.out.println(odto);//확인용
      mapper.createDetail(odto);
    }
    
  }

  @Override
  public List<OrdersDTO> list(Map map) {
    
    return mapper.list(map);
  }

  @Override
  public int total(Map map) {
    // TODO Auto-generated method stub
    return mapper.total(map);
  }

}
