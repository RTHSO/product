package com.kh.product.web.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveRest {
  @NotBlank(message = "상품명을 입력하세요")
  @Size(min=2,max=10)
  private String pname;
  @NotNull(message = "수량을 입력하세요")
  private Long quantity;
  @NotNull(message = "가격을 입력하세요")
  private Long price;
}
