package com.study.contents;

import java.util.List;
import java.util.Map;

public interface ReplyMapper {
int create(ReplyDTO dto);
  
  List<ReplyDTO> list(Map map);

  ReplyDTO read(int rnum);
 
  int update(ReplyDTO replyDTO); 
  
  int delete(int rnum);

  int total(int contentsno);
}
