package com.study.cart;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.contents.ReplyController;

@Controller
public class CartController {
  private static final Logger log = LoggerFactory.getLogger(ReplyController.class);// () 안에 컨트롤러 이름 클래스명과 동일하게 쓰시오~~

  @Autowired
  @Qualifier("com.study.cart.CartServiceImpl")
  private CartService service;

  @PostMapping("/cart/create")
  @ResponseBody
  public String create(@RequestBody CartDTO dto, HttpSession session) {

    String id = (String) session.getAttribute("id");
    dto.setId(id);
    log.info("size:" + dto.getSize());
    log.info("contentsno:" + dto.getContentsno());
    log.info("count:" + dto.getCount());
    log.info("id:" + dto.getId());

    int cnt = service.create(dto); 
    if (cnt == 1)
      return "장바구니에 저장했습니다";
    else
      return HttpStatus.INTERNAL_SERVER_ERROR.toString();
  }

  @GetMapping("/cart/list")
  public String list(HttpSession session, Model model) {

    String id = (String) session.getAttribute("id");
    model.addAttribute("list", service.list(id));

    return "/cart/list";
  }
  
  @GetMapping("/cart/delete/{cartno}")
  public String delete(@PathVariable int cartno) {
    
    service.delete(cartno);
    return "redirect:/cart/list";
  }

}