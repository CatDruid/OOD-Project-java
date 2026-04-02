package org.ood;

public class Controller {
    private UI userInterface;
    private CRUDServiceInterface<ProductService> productService;
    private EnvironmentalImpactService environmentalImpactService;
    private CRUDServiceInterface<MaterialService> materialService;
    private RecyclingGuidanceService recyclingGuidanceService;
}
