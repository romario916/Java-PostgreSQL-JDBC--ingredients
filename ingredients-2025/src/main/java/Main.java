import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        DataRetriever dr = new DataRetriever();

        try {
            // Récupérer l'ingrédient Laitue (id=1 normalement)
            Ingredient laitue = dr.findIngredientById(1);

            if (laitue != null) {
                System.out.println("Ingrédient trouvé : " + laitue.getName());

                
                Instant testDate = Instant.parse("2024-01-06T12:00:00Z");

                StockValue stockAtDate = laitue.getStockValueAt(testDate);

                System.out.println("Stock à " + testDate + " : " + stockAtDate.getQuantity() + " " + stockAtDate.getUnit());

                
            } else {
                System.out.println("Ingrédient non trouvé");
            }

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }




        
    }
}