package com.lyp.tacocloudspring.service;


import com.lyp.tacocloudspring.entity.Taco;

import java.util.List;


public interface TacoService {
    Taco saveTaco(Taco design);

    int saveIngredientToTaco(String ingredientId, String tacoId);

    List<Taco> getTacosByOrderId(String orderId);

    List<Taco> getTacosDesc12();

}
