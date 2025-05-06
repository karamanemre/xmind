package com.xmind.controller;

import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.dtos.demandAnswer.DemandAnswerRequest;
import com.xmind.models.dtos.demandAnswer.DemandAnswerResponse;
import com.xmind.models.enums.DemandStatus;
import com.xmind.services.DemandAnswerService;
import com.xmind.utils.AuthUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demand-answer")
@RequiredArgsConstructor
public class DemandAnswerController {

    private final DemandAnswerService service;

    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public DemandAnswerResponse create(@RequestBody DemandAnswerRequest request) {
        return service.create(request, AuthUtils.getCurrentUser());
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public DemandAnswerResponse update(@PathVariable Long id, @RequestBody DemandAnswerRequest request) {
        return service.update(id, request, AuthUtils.getCurrentUser());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
