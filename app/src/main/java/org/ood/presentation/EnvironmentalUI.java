package org.ood.presentation;

import java.util.Arrays;
import java.util.List;

import org.ood.application.EnvironmentalFactory;
import org.ood.application.EnvironmentalImpactService;
import org.ood.application.ProductService;
import org.ood.presentation.Helpers.InputHandler;
import org.ood.presentation.Helpers.OutputFormatter;
import org.ood.presentation.records.EntityRecords.ProductRecord;
import org.ood.presentation.records.Results.ImpactResult;

public class EnvironmentalUI implements UIInterface {
    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final ProductService productService;
    private final EnvironmentalFactory environmentalFactory;

    public EnvironmentalUI(InputHandler inputHandler, OutputFormatter outputFormatter, ProductService productService, EnvironmentalFactory environmentalFactory) {
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.productService = productService;
        this.environmentalFactory = environmentalFactory;
    }

    public void MenuLoop() {
        List<String> options = Arrays.asList(
                "Calculate environmental impact",
                "Get recycling guidance",
                "Exit");
        int choice;

        do {
            choice = inputHandler.SelectfromRange(options);
            switch (choice) {
                case 0:
                    ImpactCalculation();
                    break;
                case 1:
                    RequestGuidance();
                    break;
                default:
                    break;
            }
        } while (!options.get(choice).equals("Exit"));
    }


    public void ImpactCalculation() {

        // Get the list of available strategies
        List<String> strategies = EnvironmentalFactory.GetStringStrategies();

        // Get the products id
        outputFormatter.DisplayMessage("Enter the product id: ");
        int productId = inputHandler.GetInput(Integer.class);

        // Get the desired strategy to be used for calculation
        int strategyIndex = inputHandler.SelectfromRange(strategies);

        //Initialize service
        EnvironmentalImpactService environmentalImpactService = environmentalFactory.create(strategyIndex);
        // Get result from service
        try {
            ImpactResult res = environmentalImpactService.CalculateImpact(productId);
            // Output the result
            outputFormatter.DisplayMessage(String.format("Product: %s (%d)\nImpact: %.3f\nStrategy used: %s", res.name(), res.id(), res.impact(), strategies.get(strategyIndex)));
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage("Couldn't calculate impact : " + e.getMessage(), e.hashCode());
        }
    }

    public void RequestGuidance() {
        try{
            List<ProductRecord> productList = productService.RetrieveAll();
            if(productList != null && !productList.isEmpty()){
                outputFormatter.PrintProducts(productList);
                // Get the product's id
                outputFormatter.DisplayMessage("Enter the product's id: ");
                int productId = inputHandler.GetInput(Integer.class);

                // Output the guidance
                    String guidance = productService.GetGuidance(productId);
                    outputFormatter.DisplayMessage(guidance);
            } else {
                outputFormatter.DisplayWarningMessage("The Product list is empty.");
            }
        } catch (Exception e) {
            outputFormatter.DisplayErrorMessage("Couldn't get guidance : " + e.getMessage(), e.hashCode());
        }
    }

    
}
