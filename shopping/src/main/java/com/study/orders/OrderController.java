package com.study.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.cart.CartService;
import com.study.contents.ContentsDTO;
import com.study.contents.ContentsService;
import com.study.member.MemberDTO;
import com.study.member.MemberService;
import com.study.utility.Utility;

@Controller
public class OrderController {

  private static final Logger log = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  @Qualifier("com.study.contents.ContentsServiceImpl")
  private ContentsService cservice;
  @Autowired
  @Qualifier("com.study.member.MemberServiceImpl")
  private MemberService mservice;
  @Autowired
  @Qualifier("com.study.orders.OrderServiceImpl")
  private OrderService service;
  @Autowired
  @Qualifier("com.study.cart.CartServiceImpl")
  private CartService cartservice;

  @RequestMapping("/admin/order/list")
  public String list(HttpServletRequest request) {
    // 검색관련------------------------
    String col = Utility.checkNull(request.getParameter("col"));
    String word = Utility.checkNull(request.getParameter("word"));

    if (col.equals("total")) {
      word = "";
    }

    // 페이지관련-----------------------
    int nowPage = 1;// 현재 보고있는 페이지
    if (request.getParameter("nowPage") != null) {
      nowPage = Integer.parseInt(request.getParameter("nowPage"));
    }
    int recordPerPage = 5;// 한페이지당 보여줄 레코드갯수

    // (mysql) DB에서 가져올 순번-----------------
    int sno = (nowPage - 1) * recordPerPage;
    int eno = recordPerPage;

    Map map = new HashMap();
    map.put("col", col);
    map.put("word", word);
    map.put("sno", sno);
    map.put("eno", eno);

    int total = service.total(map);

    List<OrdersDTO> list = service.list(map);

    String paging = Utility.paging(total, nowPage, recordPerPage, col, word);

    // request에 Model사용 결과 담는다
    request.setAttribute("list", list);
    request.setAttribute("nowPage", nowPage);
    request.setAttribute("col", col);
    request.setAttribute("word", word);
    request.setAttribute("paging", paging);

    return "/order/list";

  }

  @PostMapping("/order/create/{str}")
  public String create(@PathVariable String str, int tot, String payment, String reqtext, HttpServletRequest request,
      HttpSession session) {// HttpServletRequest 선택적인 정보 받기위해서 요청뭔지
                            // HttpSession: mname이랑 id받으려고
    String id = (String) session.getAttribute("id");
    String mname = (String) session.getAttribute("mname");
    // OrdersDTO에 있는거 orders테이블로
    // 테이블 두개들어갈경우 트랜잭션 사용 => 스프링에서는 이 트랜잭션을 AOP로??

    OrdersDTO dto = new OrdersDTO();// 메모리객체생ㅇ성
    dto.setId(id);
    dto.setMname(mname);
    dto.setTotal(tot);
    dto.setPayment(payment);
    dto.setReqtext(reqtext);

    List<OrderdetailDTO> list = new ArrayList<OrderdetailDTO>();

    // 카트 주문 OR 단품 주문
    if (str.equals("cart")) {// str!=null && str.equals("cart") 로쓰는게 더정확하지만 str 꼭 들어가는거 알고있으니까
      String cno = request.getParameter("cno");// 상품번호들
      String qty = request.getParameter("qtys");// 수량들
      String size = request.getParameter("size");// 사이즈들

      String[] no = cno.split(",");// 하나로 연결되어있는거 ,로 쪼개서 배열에
      for (int i = 0; i < no.length; i++) {// 카트주문이라 상품 많으니까
        int contentsno = Integer.parseInt(no[i]);
        ContentsDTO cdto = cservice.detail(contentsno);// 디테일이나 리드 모두 ㄱㅊ
        OrderdetailDTO ddto = new OrderdetailDTO();// 상품정보읽어온거 주문ㅅ서에
        ddto.setContentsno(contentsno);
        ddto.setPname(cdto.getPname());
        ddto.setQuantity(Integer.parseInt(qty.split(",")[i]));
        ddto.setSize(size.split(",")[i]);
        list.add(ddto);
      } // for end
    } else if (str.equals("order")) {// 상품 바로주문이라 단품주문
      int contentsno = Integer.parseInt(request.getParameter("contentsno"));
      ContentsDTO cdto = cservice.detail(contentsno);
      OrderdetailDTO ddto = new OrderdetailDTO();// 상품정보읽어온거 주문ㅅ서에
      ddto.setContentsno(contentsno);
      ddto.setPname(cdto.getPname());
      ddto.setQuantity(Integer.parseInt(request.getParameter("qty")));
      ddto.setSize(request.getParameter("size"));
      list.add(ddto);
    }

    dto.setList(list);// 주문당 주문상세 정보를 갖게됌

    try {
      service.create(dto);// 주문
      if (str.equals("cart"))
        cartservice.deleteAll(id);// 장바구니 비우기(주문됐으니까)

      return "redirect:/member/mypage";
    } catch (Exception e) {
      e.printStackTrace();
      return "error";
    }
  }

  @GetMapping("/order/create/cart/{cno}/{qty}/{size}")
  public String create(@PathVariable String cno, // 모두 ,로 연결된 문자열
      @PathVariable String qty, @PathVariable String size, HttpSession session, Model model) {

    String id = (String) session.getAttribute("id");
    MemberDTO mdto = mservice.read(id);
    String[] no = cno.split(","); // "23,21" => "23", "21"
    List<ContentsDTO> list = new ArrayList<ContentsDTO>();
    for (int i = 0; i < no.length; i++) {
      int contentsno = Integer.parseInt(no[i]);
      ContentsDTO dto = cservice.detail(contentsno);
      list.add(dto);
    }

    model.addAttribute("list", list);// 상품목록
    model.addAttribute("mdto", mdto);// 회원정보(배송지등)
    model.addAttribute("qtys", qty);// 수량들(orderdetail 테이블에 저장할데이터, 총금액만들것)
    model.addAttribute("size", size);// size들(orderdetail 테이블에 저장할데이터)
    model.addAttribute("str", "cart");// 장바구니에서 주문을 한 것(상품상세보기 아니면 장바구니에서 주문한걸 표기)--어디서 받냐에따라 받는 데이터 달라짐
    model.addAttribute("cno", cno);// 상품번호들

    return "/order/create";
  }

  @GetMapping("/order/create/order/{contentsno}/{qty}/{size}") // qty는 count..
  public String create(@PathVariable int contentsno, @PathVariable int qty, @PathVariable String size,
      HttpSession session, Model model) {

    // log.info("orderController(qty/size/contentsno): " + qty + "/" + size + "/"
    // +contentsno);
    String id = (String) session.getAttribute("id");
    MemberDTO mdto = mservice.read(id);
    List<ContentsDTO> list = new ArrayList<ContentsDTO>();
    ContentsDTO dto = cservice.detail(contentsno);
    list.add(dto);

    model.addAttribute("list", list);// 상품목록
    model.addAttribute("mdto", mdto);// 회원정보(배송지등)
    model.addAttribute("qty", qty);// 수량(orderdetail 테이블에 저장할데이터, 총금액만들것)
    model.addAttribute("size", size);// size(orderdetail 테이블에 저장할데이터)
    model.addAttribute("str", "order");// 상품상세보기에서 주문을 한 것(상품상세보기 아니면 장바구니에서 주문한걸 표기)--어디서 받냐에따라 받는 데이터 달라짐
    model.addAttribute("contentsno", contentsno);// 단품상품번호

    return "/order/create";
  }

}
