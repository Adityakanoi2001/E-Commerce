package com.example.Catalog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BliCommerceProductReviews")
public class Reviews {
  @Id
  private String reviewId;
  private String productSkuId;
  private String userId;
  private String userName;
  private String userCity;
  private String reviewContent;
  private int upVotes;
  private int downVotes;

  public void setReviewContent(String reviewContent) {
    if (reviewContent != null && reviewContent.split("\\s+").length > 300) {
      throw new IllegalArgumentException("Review content cannot exceed 300 words");
    }
    this.reviewContent = reviewContent;
  }
}
