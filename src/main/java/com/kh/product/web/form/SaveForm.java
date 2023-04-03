package com.kh.product.web.form;


import lombok.Data;

@Data
public class SaveForm {
  private String pname;
  private Long quantity;
  private Long price;
}