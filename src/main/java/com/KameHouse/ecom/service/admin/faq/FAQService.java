package com.KameHouse.ecom.service.admin.faq;

import com.KameHouse.ecom.dto.FAQDto;

public interface FAQService {
    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
