package com.example.community_management.controller

import com.example.community_management.dto.AddFormDialogDto
import com.example.community_management.dto.RepresentativeFormDto
import com.example.community_management.service.FormService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/forms")
class FormController(private val formService: FormService) {

    @PostMapping("/add-form")
    fun submitAddForm(@RequestBody addForm: AddFormDialogDto): ResponseEntity<String> {
        formService.processAddForm(addForm)
        return ResponseEntity.ok("AddForm submitted successfully!")
    }

    @PostMapping("/representative-form")
    fun submitRepresentativeForm(@RequestBody representativeForm: RepresentativeFormDto): ResponseEntity<String> {
        formService.processRepresentativeForm(representativeForm)
        return ResponseEntity.ok("RepresentativeForm submitted successfully!")
    }
}