package tacos.service;

import tacos.domain.Taco;

import java.util.List;


public interface TacoService {
    String saveTaco(Taco design);

    int saveIngredientToTaco(String ingredientId, String tacoId);

    List<Taco> getTacosByOrderId(String orderId);

}
