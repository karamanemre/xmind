package com.xmind.controller;

import com.xmind.models.dtos.CommonEnumResponse;
import com.xmind.services.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CommonEnumResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
