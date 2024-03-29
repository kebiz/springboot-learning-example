package com.zengzp.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.learning.dubbo.CartService;
import com.zengzp.product.annotation.NoRepeatSubmit;
import com.zengzp.product.annotation.NotRestControllerAdvice;
import com.zengzp.product.entity.ProductInfo;
import com.zengzp.product.entity.param.PageParam;
import com.zengzp.product.service.ProductInfoService;
import com.zengzp.product.vo.ProductInfoVO;
import com.zengzp.product.vo.ResultVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 16:43
 * @description：商品控制类
 * @modified By：
 * @version: 1.0$
 */
@RestController
@RequestMapping("/product")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Reference(version = "1.0.0")
    private CartService cartService;
    @PostMapping("/productInfo")
    public ProductInfo getByVo(@Validated ProductInfoVO productInfoVO){
        QueryWrapper<ProductInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(ProductInfo::getProductName,productInfoVO.getProductName());
        queryWrapper1.lambda().eq(ProductInfo::getProductPrice,productInfoVO.getProductPrice());
        queryWrapper1.lambda().eq(ProductInfo::getProductStatus,productInfoVO.getProductStatus());
       return productInfoService.getOne(queryWrapper1);
    }
    @PostMapping("/checkReapeatSubmit")
    @NoRepeatSubmit
    public ResultVo checkSubmit(){
        QueryWrapper<ProductInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(ProductInfo::getProductId,1);
        productInfoService.getOne(queryWrapper1);
        return ResultVo.success();
    }
    @GetMapping("/findById")
    @NotRestControllerAdvice
    public String findById(){
        redisTemplate.opsForValue().set("test:redis","ts");

        return (String) redisTemplate.opsForValue().get("test:redis");
    }
    @PostMapping("/saveProductInfo")
    @NoRepeatSubmit
    public void saveProductInfo(@Validated ProductInfoVO productInfoVO){
        ProductInfo productInfo=new ProductInfo();
        BeanUtils.copyProperties(productInfoVO,productInfo);
         productInfoService.saveOrUpdate(productInfo);
    }
    @PostMapping("/findProductInfo")
    public IPage<ProductInfo> findProductInfoPage(PageParam<ProductInfo> pageParam,@RequestParam(value = "productName",required = false) String productName){
        QueryWrapper<ProductInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().like(ProductInfo::getProductName,productName==null?"":productName);
        return productInfoService.page(pageParam.createPage(),queryWrapper1);
    }
   @PostMapping("/retrySubmit")
    public void retrySubmit() throws  Exception{
       productInfoService.retrySubmit(500);
    }
    @PostMapping("/batchUpdateProductInfo")
    public void batchUpdateProductInfo() throws InterruptedException {
        productInfoService.updateStudentWithThreads();
    }

    @GetMapping("/getCartInfo")
    public List<Map<String,Object>> getCartInfo() throws InterruptedException {
       return cartService.findCartList("userName");
    }
}
