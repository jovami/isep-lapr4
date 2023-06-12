package eapli.base.formativeexam.dto.resolution;

import jovami.util.dto.Mapper;

import java.util.List;

public class FormativeExamResolutionDTOMapper implements Mapper<List<String>, FormativeExamResolutionDTO> {

    @Override
    public FormativeExamResolutionDTO toDTO(List<String> res) {

        return new FormativeExamResolutionDTO();
    }
}
