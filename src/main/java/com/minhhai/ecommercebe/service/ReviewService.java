package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.ReviewRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.PageResponse;
import com.minhhai.ecommercebe.dto.response.ReviewResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.ReviewMapper;
import com.minhhai.ecommercebe.model.Product;
import com.minhhai.ecommercebe.model.Review;
import com.minhhai.ecommercebe.model.User;
import com.minhhai.ecommercebe.repository.ProductRepository;
import com.minhhai.ecommercebe.repository.ReviewRepository;
import com.minhhai.ecommercebe.util.commons.SecurityUtil;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewResponseDTO> getAllProductReviews(Long productId) {
        List<Review> reviews = reviewRepository.findReviewByProductId(productId);

        return reviews.stream().map(reviewMapper::toResponseDTO).toList();
    }

    public ReviewResponseDTO createNewReview(ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewMapper.toEntity(reviewRequestDTO);

        Product product = productRepository.findById(reviewRequestDTO.getProductId()).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        review.setProduct(product);

        User userCreateReview = SecurityUtil.getCurrentUser();
        review.setUser(userCreateReview);

        reviewRepository.save(review);

        log.info("------------- New review created id = {} ---------------", review.getId());
        return reviewMapper.toResponseDTO(review);
    }

    public ReviewResponseDTO updateReview(Long id, @Valid ReviewRequestDTO reviewRequestDTO) {
        Review reviewUpdate = reviewRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.REVIEW_NOT_EXISTED));

        User userUpdateReview = SecurityUtil.getCurrentUser();
        if (!Objects.equals(userUpdateReview.getId(), reviewUpdate.getUser().getId())) {
            throw new AppException(ErrorCode.FORBIDDEN_UPDATE_REVIEW);
        }

        reviewUpdate.setRate(reviewRequestDTO.getRate());
        reviewUpdate.setDescription(reviewRequestDTO.getDescription());
        reviewRepository.save(reviewUpdate);

        return reviewMapper.toResponseDTO(reviewUpdate);
    }

    public Long deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_EXISTED));

        if (!Objects.equals(SecurityUtil.getCurrentUser().getId(), review.getUser().getId())
                && !SecurityUtil.getCurrentUserAuthorities().contains("delete_review")) {
            throw new AppException(ErrorCode.FORBIDDEN_DELETE_REVIEW);
        }

        reviewRepository.deleteById(id);
        return id;
    }
}
