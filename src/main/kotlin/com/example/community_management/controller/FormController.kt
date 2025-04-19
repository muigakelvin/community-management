package com.example.community_management.controller

import com.example.community_management.dto.AddFormDialogDto
import com.example.community_management.dto.RepresentativeFormDto
import com.example.community_management.dto.UnifiedRecordDto
import com.example.community_management.service.FormService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/forms")
class FormController(
    private val formService: FormService
) {

    // Submit AddFormDialog
    @PostMapping("/add-form-dialog")
    fun processAddFormDialog(@RequestBody addFormDialog: AddFormDialogDto): ResponseEntity<String> {
        formService.processAddFormDialog(addFormDialog)
        return ResponseEntity.ok("AddFormDialog submitted successfully")
    }

    // Submit RepresentativeForm
    @PostMapping("/representative-form")
    fun processRepresentativeForm(@RequestBody representativeForm: RepresentativeFormDto): ResponseEntity<String> {
        formService.processRepresentativeForm(representativeForm)
        return ResponseEntity.ok("RepresentativeForm submitted successfully")
    }

    // Read All Records
    @GetMapping("/all")
    fun getAllRecords(): ResponseEntity<List<UnifiedRecordDto>> {
        val records = formService.getAllRecords()
        return ResponseEntity.ok(records)
    }

    // Read Single Record by ID
    @GetMapping("/{id}")
    fun getRecordById(@PathVariable id: Long): ResponseEntity<UnifiedRecordDto> {
        val record = formService.getRecordById(id)
        return ResponseEntity.ok(record)
    }

    // Update Operation
    @PutMapping("/{id}")
    fun updateRecord(@PathVariable id: Long, @RequestBody updatedForm: RepresentativeFormDto): ResponseEntity<String> {
        formService.updateRecord(id, updatedForm)
        return ResponseEntity.ok("Record updated successfully")
    }

    // Delete Operation
    @DeleteMapping("/{id}")
    fun deleteRecord(@PathVariable id: Long): ResponseEntity<String> {
        formService.deleteRecord(id)
        return ResponseEntity.ok("Record deleted successfully")
    }
}