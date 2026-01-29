public class DishIngredient {
    private Integer id;
    private Ingredient ingredient;
    private Double quantityRequired;
    private UnitEnum unit;
    public DishIngredient(Integer id, Ingredient ingredient, Double quantityRequired, UnitEnum unit) {
        this.id = id;
        this.ingredient = ingredient;
        this.quantityRequired = quantityRequired;
        this.unit = unit;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    public Double getQuantityRequired() {
        return quantityRequired;
    }
    public void setQuantityRequired(Double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }
    public UnitEnum getUnit() {
        return unit;
    }
    public void setUnit(UnitEnum unit) {
        this.unit = unit;
    }

    public DishIngredient() {
        // rien à mettre ici pour l'instant
    }
    @Override
    public String toString() {
        return "DishIngredient [id=" + id + ", ingredient=" + ingredient + ", quantityRequired=" + quantityRequired
                + ", unit=" + unit + "]";
    }

    
}
