import java.util.ArrayList;
import java.util.List;

public class Dish {
    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private Double sellingPrice;
    private List<DishIngredient> ingredients = new ArrayList<>();

    public Double getDishCost() {
        double total = 0.0;
        for (DishIngredient line : ingredients) {
            total += line.getIngredient().getPrice() * line.getQuantityRequired();
        }
        return total;
    }

    public Double getGrossMargin() {
        if (sellingPrice == null) {
            throw new IllegalStateException("Selling price is null for dish: " + name);
        }
        return sellingPrice - getDishCost();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DishTypeEnum getDishType() {
        return dishType;
    }

    public void setDishType(DishTypeEnum dishType) {
        this.dishType = dishType;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public List<DishIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<DishIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Dish [id=" + id + ", name=" + name + ", dishType=" + dishType + ", sellingPrice=" + sellingPrice
                + ", ingredients=" + ingredients + "]";
    }
    


}