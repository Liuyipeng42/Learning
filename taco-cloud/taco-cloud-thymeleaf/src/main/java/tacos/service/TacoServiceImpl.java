package tacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.dao.IngredientDao;
import tacos.dao.TacoDao;
import tacos.domain.Taco;

import java.util.List;

@Service
public class TacoServiceImpl implements TacoService {

    TacoDao tacoDao;
    IngredientDao ingredientDao;

    @Autowired
    public void setTacoDao(TacoDao tacoDao) {
        this.tacoDao = tacoDao;
    }

    @Autowired
    public void setIngredientDao(IngredientDao ingredientDao){
        this.ingredientDao = ingredientDao;
    }

    @Override
    public String saveTaco(Taco design) {
        tacoDao.saveTaco(design);
        String tacoId = String.valueOf(design.getId());

        for (String ingredientId : design.getIngredients()) {
            saveIngredientToTaco(ingredientDao.findIngredientById(ingredientId).getId(), tacoId);
        }
        return tacoId;
    }

    @Override
    public int saveIngredientToTaco(String ingredientId, String tacoId) {
        return tacoDao.saveIngredientToTaco(ingredientId, tacoId);
    }

    @Override
    public List<Taco> getTacosByOrderId(String orderId) {
        return tacoDao.getTacosByOrderId(orderId);
    }
}
