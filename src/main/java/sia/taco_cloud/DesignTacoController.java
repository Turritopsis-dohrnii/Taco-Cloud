package sia.taco_cloud;

import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import sia.taco_cloud.Ingredient.Type;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")

public class DesignTacoController {

    //Now we will populate the model with attributes which will have names and values.
    @ModelAttribute
    public void addIngredientsToModel (Model model)
    {
        List <Ingredient> ingredients =Arrays.asList(new Ingredient("FLTO","Flour Tortilla",Type.WRAP), 
        new Ingredient("COTO","Corn Tortilla",Type.WRAP), 
        new Ingredient("KECH","Keema Chicken",Type.PROTEIN),
        new Ingredient("COCH","Cottage Cheese",Type.PROTEIN),
        new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES), 
        new Ingredient("LETC", "Lettuce", Type.VEGGIES),
        new Ingredient("CHED", "Cheddar", Type.CHEESE),
        new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
        new Ingredient("SLSA", "Salsa", Type.SAUCE),
        new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

        Type[] types = Ingredient.Type.values();
        for(Type type : types)
        {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients, type));
        }
    }
    
    @ModelAttribute( name= "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute( name="taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }


    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) 
    {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
    
@PostMapping
public String processTaco(Taco taco,
 @ModelAttribute TacoOrder tacoOrder) {
 tacoOrder.addTaco(taco);
 log.info("Processing taco: {}", taco);
 return "redirect:/orders/current";
}

}
