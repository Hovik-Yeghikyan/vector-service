package com.vector.vectorservice.controller;

import com.vector.vectorservice.dto.CategoryRequestDto;
import com.vector.vectorservice.dto.CategoryResponseDto;
import com.vector.vectorservice.entity.Category;
import com.vector.vectorservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;

    @GetMapping("/adminPage")
    public String adminPage() {
        return "/admin/adminPage";
    }

    @GetMapping("/adminCategory")
    public String adminCategoryPage() {
        return "/admin/categoryManagement";
    }

    @GetMapping("/addCategory")
    public String addCategoryPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        return "/admin/addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute CategoryRequestDto categoryRequestDto,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<CategoryResponseDto> byName = categoryService.existByName(categoryRequestDto.getName());
        if (byName.isEmpty()) {
             categoryService.save(categoryRequestDto, multipartFile);
            log.info("Category with {} name added successfully", categoryRequestDto.getName());
            return "redirect:/admin/addCategory?msg=Category added!";
        } else {
            log.info("Category with {} name already exists", categoryRequestDto.getName());
            return "redirect:/admin/addCategory?msg=Category name already in use";
        }
    }

    @GetMapping("/editCategory")
    public String editCategory(@RequestParam("id") Long id, ModelMap modelMap) {
        Category category = categoryService.findById(id);
        if (category != null) {
            modelMap.put("category", category);
        }
        return "/admin/editCategory";
    }

    @PostMapping("/editCategory")
    public String editProduct(@ModelAttribute CategoryResponseDto categoryResponseDto,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        categoryService.update(categoryResponseDto, multipartFile);
        log.info("category with id {} edited", categoryResponseDto.getId());
        return "redirect:/categories";
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("id") Long id) {
        categoryService.deleteById(id);
        log.info("category with id {} deleted", id);
        return "redirect:/categories";
    }
}