package org.ood.presentation;

import java.util.List;
import java.util.Scanner;

import org.ood.application.EnvironmentalImpactService;
import org.ood.application.RecyclingGuidanceService;
import org.ood.presentation.records.results.ImpactResult;

public class EnvironmentalUI implements UIInterface {
    private Scanner scanner;
    private InputHandler inputHandler;
    private OutputFormatter outputFormatter;
    private EnvironmentalImpactService environmentalImpactService;
    private RecyclingGuidanceService recyclingGuidanceService;

    public EnvironmentalUI(Scanner scanner, InputHandler inputHandler, OutputFormatter outputFormatter, EnvironmentalImpactService environmentalImpactService, RecyclingGuidanceService recyclingGuidanceService) {
        this.scanner = scanner;
        this.inputHandler = inputHandler;
        this.outputFormatter = outputFormatter;
        this.environmentalImpactService = environmentalImpactService;
        this.recyclingGuidanceService = recyclingGuidanceService;
    }

    public void ImpactCalculation() {
        // TODO error handling?

        // Get the list of available strategies
        List<String> strategies = environmentalImpactService.GetStringStrategies();

        // Get the products id
        outputFormatter.DisplayMessage("Enter the product id: ");
        int productId = inputHandler.GetInput(int.class);

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
        int productId = inputHandler.GetInput(int.class);

        // Get the respective guidance from the service
        String guidance = recyclingGuidanceService.UtilizeDefaultStrategy(productId);

        // Output the guidance
        outputFormatter.DisplayMessage(guidance);
    }

    
}
