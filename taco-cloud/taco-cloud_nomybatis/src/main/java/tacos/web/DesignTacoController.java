package tacos.web;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import tacos.domain.Order;
import tacos.domain.Taco;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@Slf4j
@Controller
// 控制器注解，让组件扫描将这个类识别为一个组件，并创建一个 DesignTacoController 实例作为 Spring 应用上下文中的 bean，
// 在SpringMVC 中，控制器Controller 负责处理由DispatcherServlet 分发的请求，
// 它把用户请求的数据经过业务处理层处理之后封装成一个Model，然后再把该Model 返回给对应的View 进行展示。

// Spring MVC：Spring 自带了一个强大的 Web 框架，名为 Spring MVC。Spring MVC 的核心是控制器（controller）的理念。
// 控制器：主要职责是处理 HTTP 请求，要么将请求传递给视图以便于渲染 HTML（浏览器展现），要么直接将数据写入响应体（RESTFUL）。
@RequestMapping("/design")
// 能够指定该控制器所处理的请求类型，不管请求是什么类型，只要访问这个路径，就会使用这个类
// 通常，只在类级别上使用@RequestMapping，以便于指定基本路径。
// 在每个处理器方法上，会使用更具体的@GetMapping、@PostMapping 等注解。
@SessionAttributes("order")
// 属性名为order的属性添加到添加到 session 中
// 在多个请求之间共用某个模型属性数据。
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private final TacoRepository designRepo;

    @Autowired // 可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping // 处理 HTTP GET 请求
    public String showDesignForm(Model model) {
        // Model 对象负责在控制器和展现数据的视图之间传递数据。

        addIngredients(model);

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design,
                                Errors errors, Order order, Model model) {
        // @Valid 注解会告诉 Spring MVC 要对提交的 Taco 对象进行校验，
        // 校验时机是在它绑定完表单数据之后、调用 processDesign()之前。
        // 若有错误，就将错误信息绑定到紧随其后的 Errors 类型的对象
        // @ModelAttribute("design") 可以将指定属性绑定到变量上，
        // 若不使用，则程序会将 model 中名为 taco（由程序推断出）的属性绑定给变量
        if (errors.hasErrors()) {
            addIngredients(model);
            return "design";
        }

        log.info("Processing design: " + design);
        design.setCreatedAt(new Date());
        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    public void addIngredients(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }
}
