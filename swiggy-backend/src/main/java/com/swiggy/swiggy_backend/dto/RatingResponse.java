package com.swiggy.swiggy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class RatingResponse {

    private Double averageRating;

    private Long totalReviews;

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Long getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(Long totalReviews) {
		this.totalReviews = totalReviews;
	}

	public RatingResponse(Double averageRating, Long totalReviews) {
		super();
		this.averageRating = averageRating;
		this.totalReviews = totalReviews;
	}
}