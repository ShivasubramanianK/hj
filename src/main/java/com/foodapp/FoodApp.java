package com.foodapp;

import com.foodapp.models.FoodItem;
import com.foodapp.models.Ingredient;
import com.foodapp.models.Recipe;
import com.foodapp.services.InventoryService;
import com.foodapp.services.RecipeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Food Management App
 */
public class FoodApp {
    private InventoryService inventoryService;
    private RecipeService recipeService;
    private Scanner scanner;

    public FoodApp() {
        this.inventoryService = new InventoryService();
        this.recipeService = new RecipeService();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        FoodApp app = new FoodApp();
        app.run();
    }

    /**
     * Main application loop
     */
    public void run() {
        System.out.println("========== Welcome to Food Management App ==========");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    inventoryMenu();
                    break;
                case 2:
                    recipeMenu();
                    break;
                case 3:
                    viewAlerts();
                    break;
                case 4:
                    running = false;
                    System.out.println("Thank you for using Food Management App!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Inventory Management");
        System.out.println("2. Recipe Management");
        System.out.println("3. View Alerts");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private void inventoryMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== INVENTORY MENU ==========");
            System.out.println("1. Add Food Item");
            System.out.println("2. View All Items");
            System.out.println("3. Search Item by Name");
            System.out.println("4. View Items by Category");
            System.out.println("5. Remove Item");
            System.out.println("6. Update Quantity");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addFoodItem();
                    break;
                case 2:
                    viewAllItems();
                    break;
                case 3:
                    searchItemByName();
                    break;
                case 4:
                    viewItemsByCategory();
                    break;
                case 5:
                    removeFoodItem();
                    break;
                case 6:
                    updateItemQuantity();
                    break;
                case 7:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void recipeMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== RECIPE MENU ==========");
            System.out.println("1. Add Recipe");
            System.out.println("2. View All Recipes");
            System.out.println("3. Search Recipe by Name");
            System.out.println("4. View Quick Recipes");
            System.out.println("5. Remove Recipe");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addRecipe();
                    break;
                case 2:
                    viewAllRecipes();
                    break;
                case 3:
                    searchRecipeByName();
                    break;
                case 4:
                    viewQuickRecipes();
                    break;
                case 5:
                    removeRecipe();
                    break;
                case 6:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addFoodItem() {
        System.out.print("\nEnter food name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category (e.g., Vegetables, Dairy, Meat): ");
        String category = scanner.nextLine();
        System.out.print("Enter quantity: ");
        double quantity = getDoubleInput();
        System.out.print("Enter unit (e.g., kg, liters, pieces): ");
        String unit = scanner.nextLine();
        System.out.print("Enter expiration date (yyyy-MM-dd): ");
        LocalDate expDate = getDateInput();
        System.out.print("Enter location (e.g., Fridge, Freezer, Pantry): ");
        String location = scanner.nextLine();
        
        FoodItem item = new FoodItem(name, category, quantity, unit, expDate, location);
        inventoryService.addItem(item);
        System.out.println("✓ Food item added successfully!");
    }

    private void viewAllItems() {
        List<FoodItem> items = inventoryService.getAllItems();
        if (items.isEmpty()) {
            System.out.println("\nNo items in inventory.");
            return;
        }
        System.out.println("\n========== ALL ITEMS ==========");
        for (FoodItem item : items) {
            System.out.println(item);
        }
    }

    private void searchItemByName() {
        System.out.print("\nEnter item name to search: ");
        String name = scanner.nextLine();
        List<FoodItem> results = inventoryService.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("No items found.");
            return;
        }
        System.out.println("\n========== SEARCH RESULTS ==========");
        for (FoodItem item : results) {
            System.out.println(item);
        }
    }

    private void viewItemsByCategory() {
        System.out.print("\nEnter category: ");
        String category = scanner.nextLine();
        List<FoodItem> items = inventoryService.getItemsByCategory(category);
        if (items.isEmpty()) {
            System.out.println("No items in this category.");
            return;
        }
        System.out.println("\n========== ITEMS IN " + category.toUpperCase() + " ==========");
        for (FoodItem item : items) {
            System.out.println(item);
        }
    }

    private void removeFoodItem() {
        System.out.print("\nEnter item ID to remove: ");
        int id = getUserChoice();
        if (inventoryService.removeItem(id)) {
            System.out.println("✓ Item removed successfully!");
        } else {
            System.out.println("Item not found.");
        }
    }

    private void updateItemQuantity() {
        System.out.print("\nEnter item ID: ");
        int id = getUserChoice();
        System.out.print("Enter new quantity: ");
        double quantity = getDoubleInput();
        if (inventoryService.updateQuantity(id, quantity)) {
            System.out.println("✓ Quantity updated successfully!");
        } else {
            System.out.println("Item not found.");
        }
    }

    private void addRecipe() {
        System.out.print("\nEnter recipe name: ");
        String name = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter servings: ");
        int servings = getUserChoice();
        System.out.print("Enter prep time (minutes): ");
        int prepTime = getUserChoice();
        System.out.print("Enter cook time (minutes): ");
        int cookTime = getUserChoice();
        System.out.print("Enter instructions: ");
        String instructions = scanner.nextLine();
        
        Recipe recipe = new Recipe(name, description, servings, prepTime, cookTime, instructions);
        
        // Add ingredients
        System.out.print("How many ingredients? ");
        int ingredientCount = getUserChoice();
        for (int i = 0; i < ingredientCount; i++) {
            System.out.print("Ingredient " + (i + 1) + " name: ");
            String ingName = scanner.nextLine();
            System.out.print("Quantity: ");
            double quantity = getDoubleInput();
            System.out.print("Unit: ");
            String unit = scanner.nextLine();
            recipe.addIngredient(new Ingredient(ingName, quantity, unit));
        }
        
        recipeService.addRecipe(recipe);
        System.out.println("✓ Recipe added successfully!");
    }

    private void viewAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        if (recipes.isEmpty()) {
            System.out.println("\nNo recipes available.");
            return;
        }
        System.out.println("\n========== ALL RECIPES ==========");
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
            System.out.println("  Ingredients:");
            for (Ingredient ing : recipe.getIngredients()) {
                System.out.println("    - " + ing);
            }
        }
    }

    private void searchRecipeByName() {
        System.out.print("\nEnter recipe name to search: ");
        String name = scanner.nextLine();
        List<Recipe> results = recipeService.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("No recipes found.");
            return;
        }
        System.out.println("\n========== SEARCH RESULTS ==========");
        for (Recipe recipe : results) {
            System.out.println(recipe);
        }
    }

    private void viewQuickRecipes() {
        List<Recipe> quickRecipes = recipeService.getQuickRecipes();
        if (quickRecipes.isEmpty()) {
            System.out.println("\nNo quick recipes available.");
            return;
        }
        System.out.println("\n========== QUICK RECIPES (< 30 min total time) ==========");
        for (Recipe recipe : quickRecipes) {
            System.out.println(recipe);
        }
    }

    private void removeRecipe() {
        System.out.print("\nEnter recipe ID to remove: ");
        int id = getUserChoice();
        if (recipeService.removeRecipe(id)) {
            System.out.println("✓ Recipe removed successfully!");
        } else {
            System.out.println("Recipe not found.");
        }
    }

    private void viewAlerts() {
        System.out.println("\n========== ALERTS ==========");
        
        List<FoodItem> expired = inventoryService.getExpiredItems();
        if (!expired.isEmpty()) {
            System.out.println("\n⚠️  EXPIRED ITEMS:");
            for (FoodItem item : expired) {
                System.out.println("  - " + item.getName() + " (Expired on " + item.getExpirationDate() + ")");
            }
        }
        
        List<FoodItem> expiring = inventoryService.getExpiringItems();
        if (!expiring.isEmpty()) {
            System.out.println("\n⏰ EXPIRING SOON (within 3 days):");
            for (FoodItem item : expiring) {
                System.out.println("  - " + item.getName() + " (Expires on " + item.getExpirationDate() + ")");
            }
        }
        
        List<FoodItem> lowStock = inventoryService.getLowStockItems();
        if (!lowStock.isEmpty()) {
            System.out.println("\n📦 LOW STOCK ITEMS:");
            for (FoodItem item : lowStock) {
                System.out.println("  - " + item.getName() + " (" + item.getQuantity() + " " + item.getUnit() + ")");
            }
        }
        
        if (expired.isEmpty() && expiring.isEmpty() && lowStock.isEmpty()) {
            System.out.println("No alerts at this time. Your inventory looks good!");
        }
    }

    private int getUserChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private LocalDate getDateInput() {
        try {
            return LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}
