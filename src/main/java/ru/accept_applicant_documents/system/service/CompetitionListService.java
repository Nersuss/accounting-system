package ru.accept_applicant_documents.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.accept_applicant_documents.system.model.CompetitionGroup;
import ru.accept_applicant_documents.system.model.CompetitionList;
import ru.accept_applicant_documents.system.repository.CompetitionGroupRepo;
import ru.accept_applicant_documents.system.repository.CompetitionListRepo;
import ru.accept_applicant_documents.system.repository.DepartmentRepo;

import java.util.List;

@Service
public class CompetitionListService {
    @Autowired
    CompetitionListRepo competitionListRepo;
    @Autowired
    CompetitionGroupRepo competitionGroupRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    CompetitionList createCompetitionList(Long departmentId)
    {
        CompetitionList competitionList = new CompetitionList();
        List<CompetitionGroup> competitionGroups = competitionGroupRepo.findAllByDepartment(departmentRepo.findById(departmentId).get());
        for (CompetitionGroup competitionGroup :competitionGroups )
        {

        }
        return competitionList;
    }
}
