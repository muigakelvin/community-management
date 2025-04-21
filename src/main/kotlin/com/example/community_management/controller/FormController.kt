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
    fun processAddFormDialog(@RequestBody addFormDialog: AddFormDialogDto): ResponseEntity<Map<String, Any>> {
        val result = formService.processAddFormDialog(addFormDialog)
        return ResponseEntity.ok(mapOf("data" to result))
    }

    // Submit RepresentativeForm
    @PostMapping("/representative-form")
    fun processRepresentativeForm(@RequestBody representativeForm: RepresentativeFormDto): ResponseEntity<Map<String, Any>> {
        val result = formService.processRepresentativeForm(representativeForm)
        return ResponseEntity.ok(mapOf("data" to result))
    }

    // Read All Records
    @GetMapping("/all")
    fun getAllRecords(): ResponseEntity<Map<String, Any>> {
        val records = formService.getAllRecords()
        return ResponseEntity.ok(mapOf("data" to records))
    }

    // Read Single Record by ID
    @GetMapping("/{id}")
    fun getRecordById(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        val record = formService.getRecordById(id)
        return ResponseEntity.ok(mapOf("data" to record))
    }

    // Update Operation
    @PutMapping("/{id}")
    fun updateRecord(@PathVariable id: Long, @RequestBody updatedForm: RepresentativeFormDto): ResponseEntity<Map<String, Any>> {
        val result = formService.updateRecord(id, updatedForm)
        return ResponseEntity.ok(mapOf("data" to result))
    }

    // Delete Operation
    @DeleteMapping("/{id}")
    fun deleteRecord(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        formService.deleteRecord(id)
        return ResponseEntity.ok(mapOf("message" to "Record deleted successfully"))
    }
}