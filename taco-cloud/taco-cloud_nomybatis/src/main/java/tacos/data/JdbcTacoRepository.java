package tacos.data;

import java.sql.*;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.domain.Ingredient;
import tacos.domain.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcTemplate jdbc;

    private final IngredientRepository ingredientRepo;

    public JdbcTacoRepository(JdbcTemplate jdbc, IngredientRepository ingredientRepo) {
        this.jdbc = jdbc;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);

        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredientRepo.findOne(ingredient), tacoId);
        }

        return taco;
    }

    private long saveTacoInfo(Taco taco) {

        String sql = "insert into taco (name, createdAt) values (?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();  // 用于获取自增长主键值
        // 通过回调获取JdbcTemplate提供的 Connection，由用户使用该 Connection 创建相关的 PreparedStatement；
        jdbc.update(connection -> {
            // 指定主键
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, taco.getName());
            preparedStatement.setTimestamp(2, new Timestamp(taco.getCreatedAt().getTime()));
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbc.update(
                "insert into taco_ingredients (taco, ingredient) values (?, ?)",
                tacoId, ingredient.getId());
    }

}
