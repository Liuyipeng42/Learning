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
import tacos.service.IngredientService;
import tacos.service.TacoService;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientService ingredientService;

    private final TacoService tacoService;

    @Autowired
    public DesignTacoController(IngredientService ingredientService, TacoService tacoService) {
        this.ingredientService = ingredientService;
        this.tacoService = tacoService;
    }

    @ModelAttribute(name = "order") // @ModelAttribute注释的方法会在此controller每个方法执行前被执行
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        addIngredients(model);
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design,
                                Errors errors, Order order, Model model) {

        if (errors.hasErrors()) {
            addIngredients(model);
            return "design";
        }

        log.info("Processing design: " + design);
        design.setCreatedAt(new Date());
        tacoService.saveTaco(design);
        order.addDesign(design);

        return "redirect:/orders/current";
    }

    public void addIngredients(Model model) {
        List<Ingredient> ingredients = new ArrayList<>(ingredientService.findAllIngredients());
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            System.out.println(type.toString().toLowerCase());
            System.out.println(filterByType(ingredients, type));
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
