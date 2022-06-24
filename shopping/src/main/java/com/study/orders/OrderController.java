package com.study.orders;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.study.contents.ContentsDTO;
import com.study.contents.ContentsService;
import com.study.member.MemberDTO;
import com.study.member.MemberService;

@Controller
public class OrderController {

  private static final Logger log = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  @Qualifier("com.study.contents.ContentsServiceImpl")
  private ContentsService cservice;
  
  @Autowired
  @Qualifier("com.study.member.MemberServiceImpl")
  private MemberService mservice;
  
  //상세페이지에서 주문 눌러서 요청한코드
  @GetMapping("/order/create/order/{contentsno}/{quantity}/{size}")
  public String create(
      @PathVariable int contentsno,
      @PathVariable int quantity,
      @PathVariable String size,
      HttpSession session,
      Model model
      ) {
    //log.info("orderController"+size);
    //log.info("orderController"+quantity);
    //log.info("orderController"+contentsno);
    
    String id = (String)session.getAttribute("id");
    MemberDTO mdto = mservice.read(id);
    // 여러개일수있으니 list 로 ContentDTO 타입으로 담는다.
    List<ContentsDTO> list = new ArrayList<ContentsDTO>();
    ContentsDTO dto = cservice.read(contentsno);
    list.add(dto);
    
    //상품에 대한정보 = list 하나여도,여러개여도
    model.addAttribute("list",list);
    //회원에 대한 정보 (배송지같은)
    model.addAttribute("mdto",mdto);
    //주문에 대한 정보  (수량 orderdetail 테이블에 저장, 총금액)
    model.addAttribute("qty",quantity);
    //size (orderdetail 테이블에 저장)    
    model.addAttribute("size",size);
    //문자열에 값에 따라서 처리되는게 다르다 ,상품상세보기에서 주문을 확인 (어디서부터온건지) 카트에서 바로 오는경우는 단품일수도 있고 아닐수도 있으니까 split 같은거 이용해야한다
    model.addAttribute("str","order");
    // 단품 상품번호
    model.addAttribute("contentsno",contentsno);
    return "/order/create";
  }
  
  
  //카트에서 order 로 넘어오는경우 (나중에 처리하는 jsp 페이지를 나눠서 하는것도 방법이다) 
  //장바구니에서 car 눌러서 요청한 코드
  @GetMapping("/order/create/cart/{cno}/{quantity}/{size}")
  public String cart(
      @PathVariable String cno,
      @PathVariable String quantity,
      @PathVariable String size,
      HttpSession session,
      Model model
      ) {
    
    String id = (String)session.getAttribute("id");
    MemberDTO mdto = mservice.read(id);
    
    String[] no = cno.split(","); //"23,21" => "23","21"
    List<ContentsDTO> list = new ArrayList<ContentsDTO>();
    for(int i = 0 ; i< no.length ; i++) {
      int contentsno = Integer.parseInt(no[i]);//정수형으로 바꿔준다.
      ContentsDTO dto = cservice.detail(contentsno);
      list.add(dto);
    }
    
   
    model.addAttribute("list",list); //상품목록
    model.addAttribute("mdto",mdto); //회원목록
    model.addAttribute("quantity",quantity); //수량들
    model.addAttribute("size",size); //사이즈들
    model.addAttribute("cno",cno); //상품들 번호
    //문자열에 값에 따라서 처리되는게 다르다 ,상품상세보기에서 주문을 확인 (어디서부터온건지) 카트에서 바로 오는경우는 단품일수도 있고 아닐수도 있으니까 split 같은거 이용해야한다
    model.addAttribute("str","cart"); //장구니에서 주문을한것
    
    return "/order/create";
  }
}
