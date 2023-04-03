package com.kh.product.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private Long productId;
  private String pname;
  private Long quantity;
  private Long price;
}
