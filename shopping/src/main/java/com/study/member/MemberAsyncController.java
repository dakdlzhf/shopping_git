package com.study.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberAsyncController {
  @Autowired
  @Qualifier("com.study.member.MemberServiceImpl")
  private MemberService service;
  
  @RequestMapping(value = "/async/idFind", method = RequestMethod.GET)
  public ResponseEntity<MemberDTO> idFind(@RequestParam(name ="mname")String mname,@RequestParam(name ="email")String email) {
    
    Map map = new HashMap();
    map.put("mname", mname);
    map.put("email", email);
    
    return new ResponseEntity<MemberDTO>(service.readFind(map), HttpStatus.OK);
  }
  
  @RequestMapping(value = "/async/pwFind", method = RequestMethod.GET)
  public ResponseEntity<MemberDTO> pwFind(@RequestParam(name ="mname")String mname,@RequestParam(name ="id")String id) {
    
    Map map = new HashMap();
    map.put("mname", mname);
    map.put("id", id);
    
    return new ResponseEntity<MemberDTO>(service.readFind(map), HttpStatus.OK);
  }
  

}
