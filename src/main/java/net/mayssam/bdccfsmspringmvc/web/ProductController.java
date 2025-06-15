package net.mayssam.bdccfsmspringmvc.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import net.mayssam.bdccfsmspringmvc.entities.Product;
import net.mayssam.bdccfsmspringmvc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/user/index")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("productsList", products);
        return "products";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }
    @PostMapping("/admin/delete")
    public String delete (@RequestParam(name = "id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }
    @GetMapping("/admin/newProduct")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "new-product";
    }
    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "new-product";
        productRepository.save(product);
        return "redirect:/user/index";

    }
    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";

        }

    @GetMapping("/login")
    public String login(){
        return "login";

    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";

    }
    @GetMapping("/user/search")
    public String searchProducts(@RequestParam(name = "keyword") String keyword, Model model) {
        List<Product> products = productRepository.findByNameContainsIgnoreCase(keyword);
        model.addAttribute("productsList", products);
        return "products";
    }
    @GetMapping("/admin/editProduct")
    public String editProduct(@RequestParam(name = "id") Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return "redirect:/user/index";
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/admin/updateProduct")
    public String updateProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "edit-product";
        productRepository.save(product);
        return "redirect:/user/index";
    }



}
