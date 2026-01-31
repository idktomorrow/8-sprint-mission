package com.sprint.mission.discodeit.dto.data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

  private UUID id; //
  private Instant createdAt; //
  private Instant updatedAt; //
  private String content; //
  private UUID channelId; //
  private UserDto author;
  private List<BinaryContentDto> attachments;
}