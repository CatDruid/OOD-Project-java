package org.ood.presentation;

import java.util.Arrays;
import java.util.List;

import org.ood.application.EnvironmentalFactory;
import org.ood.application.EnvironmentalImpactService;
import org.ood.application.ProductService;
import org.ood.presentation.records.EntityRecords.ProductRecord;
import org.ood.presentation.records.Results.ImpactResult;

public class EnvironmentalUI implements UIInterface {
    private final InputHandler inputHandler;
    private final OutputFormatter outputFormatter;
    private final ProductService productService;

    public EnvironmentalUI(InputHandler inputHandler, OutputFormatter outputFormatter, ProductService productService) {
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.productService = productService;
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
        // TODO error handling?

        // Get the list of available strategies
        List<String> strategies = EnvironmentalFactory.GetStringStrategies();

        // Get the products id
        outputFormatter.DisplayMessage("Enter the product id: ");
        int productId = inputHandler.GetInput(Integer.class);

        // Get the desired strategy to be used for calculation
        int strategyIndex = inputHandler.SelectfromRange(strategies);

        //Initialize service
        EnvironmentalImpactService environmentalImpactService = EnvironmentalFactory.create(strategyIndex);
        // Get result from service
        ImpactResult res = environmentalImpactService.CalculateImpact(productId, strategyIndex);

        // Output the result
        outputFormatter.DisplayMessage(String.format("Product: %s (%d)\nImpact: %f\nStrategy used: %s", res.name(), res.id(), res.impact(), strategies.get(strategyIndex)));
    }

    public void RequestGuidance() {
        try{
            List<ProductRecord> productList = productService.RetrieveAll();
            if(productList != null){
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
            throw new RuntimeException(e);
        }
    }

    
}
