import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private final DBConnection dbConnection = new DBConnection();
// Méthode pour trouver un ingrédient + ses mouvements
public Ingredient findIngredientById(Integer id) {
    if (id == null) throw new IllegalArgumentException("ID null");

    String sql = """
        SELECT 
            i.id AS ing_id, i.name, i.price, i.category,
            sm.id AS sm_id, sm.quantity, sm.type, sm.unit, sm.creation_datetime
        FROM ingredient i
        LEFT JOIN stockmovement sm ON sm.id_ingredient = i.id
        WHERE i.id = ?
        ORDER BY sm.creation_datetime
        """;

    Ingredient ingredient = null;
    List<StockMovement> movements = new ArrayList<>();

    try (Connection conn = dbConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                if (ingredient == null) {
                    ingredient = new Ingredient(
                        rs.getInt("ing_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        CategoryEnum.valueOf(rs.getString("category"))
                    );
                }

                // Si mouvement présent
                if (rs.getObject("sm_id") != null) {
                    StockValue value = new StockValue(
                        rs.getDouble("quantity"),
                        UnitEnum.valueOf(rs.getString("unit"))
                    );

                    StockMovement mvt = new StockMovement();
                    mvt.setId(rs.getInt("sm_id"));
                    mvt.setValue(value);
                    mvt.setType(MovementTypeEnum.valueOf(rs.getString("type")));
                    mvt.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());

                    movements.add(mvt);
                }
            }
        }

        if (ingredient == null) {
            throw new RuntimeException("Ingrédient non trouvé avec id = " + id);
        }

        ingredient.setStockMovementList(movements);
        return ingredient;

    } catch (SQLException e) {
        throw new RuntimeException("Erreur récupération ingrédient " + id, e);
    }
}

// Méthode saveIngredient
public Ingredient saveIngredient(Ingredient toSave) {
    if (toSave == null || toSave.getName() == null) {
        throw new IllegalArgumentException("Ingrédient incomplet");
    }

    Connection conn = null;
    try {
        conn = dbConnection.getConnection();
        conn.setAutoCommit(false);

        // 1. Sauvegarde / update ingrédient
        Integer ingId = upsertIngredient(conn, toSave);

        // 2. On ajoute les nouveaux mouvements (sans supprimer les anciens)
        if (toSave.getStockMovementList() != null && !toSave.getStockMovementList().isEmpty()) {
            insertStockMovements(conn, ingId, toSave.getStockMovementList());
        }

        conn.commit();

        // Recharge complet
        return findIngredientById(ingId);

    } catch (SQLException e) {
        if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
        throw new RuntimeException("Erreur sauvegarde ingrédient", e);
    } finally {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
    }
}

private Integer upsertIngredient(Connection conn, Ingredient ing) throws SQLException {
    String sql = """
        INSERT INTO ingredient (id, name, price, category)
        VALUES (?, ?, ?, ?::ingredient_category)
        ON CONFLICT (id) DO UPDATE SET
            name = EXCLUDED.name,
            price = EXCLUDED.price,
            category = EXCLUDED.category
        RETURNING id
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        if (ing.getId() != null) ps.setInt(1, ing.getId()); else ps.setNull(1, Types.INTEGER);
        ps.setString(2, ing.getName());
        ps.setDouble(3, ing.getPrice());
        ps.setString(4, ing.getCategory().name());

        try (ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }
}

private void insertStockMovements(Connection conn, Integer ingId, List<StockMovement> movements) throws SQLException {
    String sql = """
        INSERT INTO stockmovement (id_ingredient, quantity, type, unit, creation_datetime)
        VALUES (?, ?, ?::movement_type, ?::unit_type, ?)
        ON CONFLICT DO NOTHING
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        for (StockMovement mvt : movements) {
            ps.setInt(1, ingId);
            ps.setDouble(2, mvt.getValue().getQuantity());
            ps.setString(3, mvt.getType().name());
            ps.setString(4, mvt.getValue().getUnit().name());
            ps.setTimestamp(5, Timestamp.from(mvt.getCreationDatetime()));
            ps.addBatch();
        }
        ps.executeBatch();
    }
}
   
}