package com.sprint.mission.discodeit.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

  private List<T> content;      // 실제 데이터 목록
  private int number;           // 현재 페이지 번호
  private int size;             // 한 페이지당 개수
  private Long totalElements;   // 전체 개수 (Slice면 null 가능)
}