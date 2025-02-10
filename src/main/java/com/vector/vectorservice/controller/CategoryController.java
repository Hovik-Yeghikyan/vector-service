package com.vector.vectorservice.controller;

import com.vector.vectorservice.dto.CategoryResponseDto;
import com.vector.vectorservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String showCategories(ModelMap modelMap,
                                 @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "pageSize", defaultValue = "8") int pageSize) {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<CategoryResponseDto> categoryPage = categoryService.findAll(pageRequest);
        int totalPages = categoryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.put("categories", categoryPage);

        return "/categoryPage";
    }
}
