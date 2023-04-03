package com.kh.product.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO{

  private final NamedParameterJdbcTemplate template;
  
  // 등록
  @Override
  public Long save(Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product(product_id,pname,quantity,price) ");
    sql.append("values(product_product_id_seq.nextval, :pname, :quantity, :price) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(product);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"product_id"});

    long productId = keyHolder.getKey().longValue();

    return productId;
  }

  // 조회
  @Override
  public Optional<Product> findById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, quantity, price ");
    sql.append("  from product ");
    sql.append(" where product_id = :id ");
    try {
      Map<String, Long> param = Map.of("id", productId);

      Product product = template.queryForObject(
          sql.toString(), param, productRowMapper());
      log.info("product={}",product);
      return Optional.of(product);
    } catch (EmptyResultDataAccessException e) {
      //조회결과가 없는 경우
      return Optional.empty();
    }
  }

  // 수정
  @Override
  public int update(Long productId, Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("update product ");
    sql.append("   set pname = :pname, ");
    sql.append("       quantity = :quantity, ");
    sql.append("       price = :price ");
    sql.append(" where product_id = :id ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("pname",product.getPname())
        .addValue("quantity",product.getQuantity())
        .addValue("price",product.getPrice())
        .addValue("id",productId);

    return template.update(sql.toString(),param);
  }

  // 삭제
  @Override
  public int delete(Long productId) {
    String sql = "delete from product where product_id = :id ";
    return template.update(sql,Map.of("id",productId));
  }

  // 전체조회
  @Override
  public List<Product> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, quantity, price ");
    sql.append("  from product ");

    List<Product> list = template.query(sql.toString(),BeanPropertyRowMapper.newInstance(Product.class));
    return list;
  }

  private RowMapper<Product> productRowMapper() {
    return (rs, rowNum) -> {
      Product product = new Product();
      product.setProductId(rs.getLong("product_id"));
      product.setPname(rs.getString("pname"));
      product.setQuantity(rs.getLong("quantity"));
      product.setPrice(rs.getLong("price"));
      return product;
    };
  }
}

