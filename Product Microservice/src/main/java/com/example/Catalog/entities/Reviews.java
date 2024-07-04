package com.example.Catalog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

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
    if (reviewContent != null && reviewContent.split("\\s+").length > 200) {
      throw new IllegalArgumentException("Review content cannot exceed 200 words");
    }
    this.reviewContent = reviewContent;
  }
}
