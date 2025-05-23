package com.xmind.controller;

import com.xmind.elasticsearch.document.DemandDocument;
import com.xmind.models.dtos.CommonEnumResponse;
import com.xmind.models.dtos.demand.DemandAdminResponse;
import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.dtos.demand.DemandSetStatusRequest;
import com.xmind.models.enums.DemandStatus;
import com.xmind.services.DemandService;
import com.xmind.utils.AuthUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demand")
@RequiredArgsConstructor
public class DemandController {

    private final DemandService service;

    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public DemandResponse create(@RequestBody DemandRequest request) {
        return service.create(request, AuthUtils.getCurrentUser());
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public DemandResponse update(@PathVariable Long id, @RequestBody DemandRequest request) {
        return service.update(id, request, AuthUtils.getCurrentUser());
    }

    @PutMapping("/{id}/set-status")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public void setStatus(@PathVariable Long id, @RequestBody DemandSetStatusRequest request) {
        service.setStatus(id, request.getStatus());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public DemandResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public List<DemandResponse> getAll(@PageableDefault(sort = {"createdDate"}, page = 0, size = 10) Pageable pageable,
                                       @RequestParam(required = false) DemandStatus status) {
        return service.getAll(pageable, status, AuthUtils.getCurrentUser());
    }

    @GetMapping("/search")
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    public List<DemandDocument> getBySearchParam(@RequestParam String title) {
        return service.getBySearchParam(AuthUtils.getCurrentUser(), title);
    }

    @GetMapping("/all")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public List<DemandAdminResponse> getAllDemandsForAdmin(@PageableDefault(sort = {"createdDate"}, page = 0, size = 10) Pageable pageable,
                                                           @RequestParam(required = false) DemandStatus status) {
        return service.getAllDemandsForAdmin(pageable, status);
    }

    @GetMapping("/statuses")
    public List<CommonEnumResponse> getStatuses() {
        return service.getStatuses();
    }
}
