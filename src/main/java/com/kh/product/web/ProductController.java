package com.kh.product.web;

import com.kh.product.dao.Product;
import com.kh.product.svc.ProductSVC;
import com.kh.product.web.form.DetailForm;
import com.kh.product.web.form.SaveForm;
import com.kh.product.web.form.UpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  //등록양식
  @GetMapping("/add")
  public String saveForm(){

    return "product/saveForm";
  }

  //등록처리
  @PostMapping("/add")
  public String save(
      @ModelAttribute SaveForm saveForm,
      RedirectAttributes redirectAttributes
  ){
    log.info("saveForm={}",saveForm);
    //등록
    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice(saveForm.getPrice());

    Long savedProductId = productSVC.save(product);
    redirectAttributes.addAttribute("id",savedProductId);
    return "redirect:/products/{id}/detail";
  }

  //조회
  @GetMapping("/{id}/detail")
  public String findById(
      @PathVariable("id") Long id,
      Model model
  ){
    Optional<Product> findedProduct = productSVC.findById(id);
    Product product = findedProduct.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setProductId(product.getProductId());
    detailForm.setPname(product.getPname());
    detailForm.setQuantity(product.getQuantity());
    detailForm.setPrice(product.getPrice());

    model.addAttribute("form",detailForm);
    return "product/detailForm";
  }

  //수정양식
  @GetMapping("/{id}/edit")
  public String updateForm(
      @PathVariable("id") Long id,
      Model model
  ){
    Optional<Product> findedProduct = productSVC.findById(id);
    Product product = findedProduct.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setProductId(product.getProductId());
    updateForm.setPname(product.getPname());
    updateForm.setQuantity(product.getQuantity());
    updateForm.setPrice(product.getPrice());

    model.addAttribute("form",updateForm);
    return "product/updateForm";
  }

  //수정
  @PostMapping("/{id}/edit")
  public String update(
      @PathVariable("id") Long productId,
      @ModelAttribute("form") UpdateForm updateForm,
      RedirectAttributes redirectAttributes
  ){

    Product product = new Product();
    product.setProductId(productId);
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());

    productSVC.update(productId, product);

    redirectAttributes.addAttribute("id",productId);
    return "redirect:/products/{id}/detail";
  }

  //삭제
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long productId){

    productSVC.delete(productId);

    return "redirect:/products";
  }

  // 전체 조회
  @GetMapping
  public String findAll(Model model){

    List<Product> products = productSVC.findAll();
    model.addAttribute("products",products);
    return "product/all";
  }

}
