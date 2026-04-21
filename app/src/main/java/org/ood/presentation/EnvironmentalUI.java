package org.ood.presentation;

import java.util.Arrays;
import java.util.List;

import org.ood.application.EnvironmentalImpactService;
import org.ood.application.RecyclingGuidanceService;
import org.ood.presentation.records.Results.ImpactResult;

public class EnvironmentalUI implements UIInterface {
    private InputHandler inputHandler;
    private OutputFormatter outputFormatter;
    private EnvironmentalImpactService environmentalImpactService;
    private RecyclingGuidanceService recyclingGuidanceService;

    public EnvironmentalUI(InputHandler inputHandler, OutputFormatter outputFormatter, EnvironmentalImpactService environmentalImpactService, RecyclingGuidanceService recyclingGuidanceService) {
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.environmentalImpactService = environmentalImpactService;
        this.recyclingGuidanceService = recyclingGuidanceService;
    }

    public void MenuLoop() {
        List<String> options = Arrays.asList(new String[]{
            "Calculate environmental impact", 
            "Get recycling guidance", 
            "Exit"
        });
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
        List<String> strategies = environmentalImpactService.GetStringStrategies();

        // Get the products id
        outputFormatter.DisplayMessage("Enter the product id: ");
        int productId = inputHandler.GetInput(Integer.class);

        // Get the desired strategie to be used for calculation
        int strategyIndex = inputHandler.SelectfromRange(strategies);

        // Get result from service
        ImpactResult res = environmentalImpactService.CalculateImpact(productId, strategyIndex);

        // Output the result
        outputFormatter.DisplayMessage(String.format("Product: %s (%d)\nImpact: %d\nStrategy used: %s", res.name(), res.id(), res.impact(), strategies.get(strategyIndex)));
    }

    public void RequestGuidance() {
        // Get the products id
        outputFormatter.DisplayMessage("Enter the products id: ");
        int productId = inputHandler.GetInput(Integer.class);

        // Get the respective guidance from the service
        String guidance = recyclingGuidanceService.UtilizeDefaultStrategy(productId);

        // Output the guidance
        outputFormatter.DisplayMessage(guidance);
    }

    
}
