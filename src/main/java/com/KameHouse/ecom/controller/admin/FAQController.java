package com.KameHouse.ecom.controller.admin;

import com.KameHouse.ecom.dto.FAQDto;
import com.KameHouse.ecom.service.admin.faq.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;

    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> postFAQ(@PathVariable Long productId, @RequestBody FAQDto faqDto) {
        FAQDto createdFaqDto = faqService.postFAQ(productId, faqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaqDto);
    }

}
