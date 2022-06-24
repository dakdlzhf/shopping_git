package com.study.cart;

import java.util.List;

public interface CartMapper {

  List<CartDTO> list(String id);

  int create(CartDTO cartDTO);

  int update(CartDTO cartDTO);

  void delete(int cartno);

  int total(int contentsno);
}
