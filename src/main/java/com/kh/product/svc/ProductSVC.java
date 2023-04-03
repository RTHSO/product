package com.kh.product.svc;

import com.kh.product.dao.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {

  // 등록
  Long save(Product product);

  // 조회
  Optional<Product> findById(Long productId);

  // 수정
  int update(Long productId, Product product);

  // 삭제
  int delete(Long productId);

  // 전체조회
  List<Product> findAll();
}
