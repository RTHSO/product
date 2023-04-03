package com.kh.product.web.form;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveForm {
  @NotNull
  private String pname;
  @NotNull
  private Long quantity;
  @NotNull
  private Long price;
}