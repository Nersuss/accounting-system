package ru.accept_applicant_documents.system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.accept_applicant_documents.system.model.CompetitionGroup;
import ru.accept_applicant_documents.system.model.Document;
import ru.accept_applicant_documents.system.model.Order;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersSnils {
    private List<Order> orders;
    private List<Document> documents;
}
