package com.convertino.hire.mapper;

import com.convertino.hire.dto.request.MessageRequestDTO;
import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.response.MessageResponseDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.Message;
import com.convertino.hire.model.Skill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @InheritInverseConfiguration
//    @Mapping(source = "interview.id", target = "interviewId")
    MessageResponseDTO mapToDTO(Message message);

    Message mapToMessage(MessageRequestDTO messageRequestDTO);

    List<MessageResponseDTO> mapToDTO(List<Message> messages);
}
