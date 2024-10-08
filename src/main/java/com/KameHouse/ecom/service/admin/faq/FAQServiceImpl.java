package com.KameHouse.ecom.service.admin.faq;

import com.KameHouse.ecom.dto.FAQDto;
import com.KameHouse.ecom.entity.FAQ;
import com.KameHouse.ecom.entity.Product;
import com.KameHouse.ecom.repo.FAQRepository;
import com.KameHouse.ecom.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    private final ProductRepository productRepository;

    @Override
    public FAQDto postFAQ(Long productId, FAQDto faqDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            FAQ faq = new FAQ();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());
            FAQ createdFaq = faqRepository.save(faq);
            FAQDto createdFaqDto = new FAQDto();
            createdFaqDto.setId(createdFaq.getId());
            return createdFaqDto;
        }
        return null;
    }
}
