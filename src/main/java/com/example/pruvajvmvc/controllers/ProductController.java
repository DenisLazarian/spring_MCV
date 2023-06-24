package com.example.pruvajvmvc.controllers;

import com.example.pruvajvmvc.entities.Product;
import com.example.pruvajvmvc.repository.ProductRepository;
import com.example.pruvajvmvc.services.HelpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private HelpService helpService;
    private ProductRepository repository;
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * GET http://localhost:8080/products
     * */
    @GetMapping
    public String findAll( Model model ){
        List<Product> products = this.repository.findAll();
        model.addAttribute("products", products);
        return "products-list";
    }

    @GetMapping("{id}/show")
    public String showProduct( Model model, @PathVariable("id") long id ){
        List<Product> products = this.repository.findAll();
        Product product = products.get(((int) id)-1);
        model.addAttribute("product", product);

        return "show-product";
    }

    @GetMapping("{id}/edit")
    public String editForm( Model model, @PathVariable("id") long id ){
        List<Product> products = this.repository.findAll();
        Product product = products.get(((int) id)-1);

        model.addAttribute("title", "Edit Product");
        model.addAttribute("product", product);

        return "form-edit";
    }

    @PostMapping("{id}/update")
    public String update( @PathVariable("id") long id , @RequestParam MultiValueMap<String, String> allParams, RedirectAttributes redAttrs){
        List<Product> products = this.repository.findAll();
        Product productUpd = products.get(((int) id)-1);
        boolean error = false;

        List<String> errors = new LinkedList<>();

        if(allParams.get("title").get(0).isEmpty()){
            errors.add("The title can not be empty, it's required.");
            error = true;
        }else{
            productUpd.setTitle(allParams.get("title").get(0));
        }

        if(!allParams.get("price").get(0).isEmpty()){
            if(Double.parseDouble(allParams.get("price").get(0)) < 0){
                errors.add("The price can not be a negative number.");
                error = true;
            }else
                productUpd.setPrice(Double.parseDouble(allParams.get("price").get(0)));

        }else{
            errors.add("The price can not be empty, it's required.");
            error = true;
        }

        if(!allParams.get("stock").get(0).isEmpty()){
            productUpd.setQuantity(Integer.parseInt(allParams.get("stock").get(0)));
        }else{
            errors.add("The stock can not be empty, it's required.");
            error = true;
        }

        for (Product product : products) {
            if (product.getTitle().equals(productUpd.getTitle()) && product.getId() != id ) {
                errors.add("The title can not be repeated with others products.");
                error = true;
            }
        }

        if(error){
            redAttrs.addFlashAttribute("errors", errors);
            redAttrs.addFlashAttribute("oldTitle", allParams.get("title").get(0));
            redAttrs.addFlashAttribute("oldPrice", allParams.get("price").get(0));
            redAttrs.addFlashAttribute("oldStock", allParams.get("stock").get(0));

            return "redirect:/products/"+id+"/edit";
        }

        redAttrs.addFlashAttribute("success", "Products updated with exit.");
        this.repository.saveAndFlush(productUpd);

        return "redirect:/products";
    }

    @PostMapping("{id}/delete")
    public String delete( @PathVariable("id") long id ){
        List<Product> products = this.repository.findAll();
        Product product = products.get(((int) id)-1);
        this.repository.delete(product);

        return "redirect:/products";
    }

    @GetMapping("create")
    public String create(Model model){

        model.addAttribute("title", "New product");
        return "form-create";
    }

    @PostMapping("save")
    public String create(@RequestParam MultiValueMap<String, String> allParams, RedirectAttributes redAttrs){
        List<Product> products = this.repository.findAll();
        Product productCrt = new Product();
        boolean error = false;

        List<String> errors = new LinkedList<>();

        if(allParams.get("title").get(0).isEmpty()){
            errors.add("The title can not be empty, it's required.");
            error = true;
        }else{
            productCrt.setTitle(allParams.get("title").get(0));
        }

        if(!allParams.get("price").get(0).isEmpty()){
            if(Double.parseDouble(allParams.get("price").get(0)) < 0){
                errors.add("The price can not be a negative number.");
                error = true;
            }else
                productCrt.setPrice(Double.parseDouble(allParams.get("price").get(0)));

        }else{
            errors.add("The price can not be empty, it's required.");
            error = true;
        }

        if(!allParams.get("stock").get(0).isEmpty()){
            productCrt.setQuantity(Integer.parseInt(allParams.get("stock").get(0)));
        }else{
            errors.add("The stock can not be empty, it's required.");
            error = true;
        }

        for (Product product : products) {
            if (product.getTitle().equals(productCrt.getTitle()) ) {
                errors.add("The title can not be repeated with others products.");
                error = true;
            }
        }

        if(error){
            redAttrs.addFlashAttribute("errors", errors);
            redAttrs.addFlashAttribute("oldTitle", allParams.get("title").get(0));
            redAttrs.addFlashAttribute("oldPrice", allParams.get("price").get(0));
            redAttrs.addFlashAttribute("oldStock", allParams.get("stock").get(0));

            return "redirect:/products/create";
        }

        redAttrs.addFlashAttribute("success", "Products saved with exit.");
        this.repository.saveAndFlush(productCrt);

        return "redirect:/products";
    }
}
