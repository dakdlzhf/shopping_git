package com.study.cart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.study.cart.CartServiceImpl")
public class CartServiceImpl implements CartService {

  @Autowired
  private CartMapper mapper;

  @Override
  public List<CartDTO> list(String id) {
    // TODO Auto-generated method stub
    return mapper.list(id);
  }

  @Override
  public int update(CartDTO cartDTO) {
    // TODO Auto-generated method stub
    return mapper.update(cartDTO);
  }

  @Override
  public void delete(int cartno) {
    mapper.delete(cartno);
  }

  @Override
  public int total(int contentsno) {
    // TODO Auto-generated method stub
    return mapper.total(contentsno);
  }

  @Override
  public int create(CartDTO cartDTO) {
    // TODO Auto-generated method stub
    return mapper.create(cartDTO);
  }

}
