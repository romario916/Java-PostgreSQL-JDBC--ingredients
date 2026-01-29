import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ingredient {
    private Integer id;
    private String name;
    private Double price;
    private CategoryEnum category;

    // Liste des mouvements de stock
    private List<StockMovement> stockMovementList = new ArrayList<>();

    // Constructeurs
    public Ingredient() {}

    public Ingredient(Integer id, String name, Double price, CategoryEnum category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public CategoryEnum getCategory() { return category; }
    public void setCategory(CategoryEnum category) { this.category = category; }

    public List<StockMovement> getStockMovementList() {
        return new ArrayList<>(stockMovementList); // copie pour sécurité
    }

    public void setStockMovementList(List<StockMovement> stockMovementList) {
        this.stockMovementList = stockMovementList != null ? new ArrayList<>(stockMovementList) : new ArrayList<>();
    }

    // Méthode demandée : calcul du stock à une date donnée
    public StockValue getStockValueAt(Instant t) {
        if (t == null) {
            throw new IllegalArgumentException("La date ne peut pas être null");
        }

        double totalQuantity = 0.0;
        UnitEnum unit = null; // on prend la première unité rencontrée (tout en KG ici)

        for (StockMovement mvt : stockMovementList) {
            if (mvt.getCreationDatetime() == null || mvt.getCreationDatetime().isAfter(t)) {
                continue; // ignorer les mouvements futurs
            }

            double qty = mvt.getValue().getQuantity();

            if (mvt.getType() == MovementTypeEnum.IN) {
                totalQuantity += qty;
            } else if (mvt.getType() == MovementTypeEnum.OUT) {
                totalQuantity -= qty;
            }

            if (unit == null) {
                unit = mvt.getValue().getUnit();
            }
        }

        // Si aucun mouvement, stock = 0
        if (unit == null) {
            unit = UnitEnum.KG;
        }

        return new StockValue(totalQuantity, unit);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", stockMovements=" + stockMovementList.size() +
                '}';
    }
}