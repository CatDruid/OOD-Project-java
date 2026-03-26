# OOD-Project-java

Sustainable Product and Recycling Managenent System - SPaRMS

SPaRMS gives users the ability to manage products with a focus on environmental sustainability.
It calculates and gives tips on recycling for both single and mixed material products and gives an estimated calculation of the environmental impact. 

## Roles
Dag Sandström - Product management dev 

David Charlier - Material management dev 

Alessandro Antonio Pretti Marin - Environmental impact dev 

Shared - Recycling / Repo management / Docs / UI / Design

## Branch naming

#### Features:
```
feature/category/name
```
#### Fixes and refactors:
```
type/category/date/name
```
#### Other branches:
```
devolpment -- Preparation and integration before releases
docs -- Branch for documentation
```

## Requirements

### Functional requirements

1. Product
    - Attributes: name, ,lifespan, materials, category, environental impact
    - Calculate its own Environmental impact with different formulas
    - Get list of materials
2. Material
    - Attributes: name, environmental impact value, category
    - Materials must be reusable across multiple products
3. Product management
    - List all products
    - View detailed product information
    - Add, delete, manipulate products
4. Material management
    - List all materials (?)
    - View detailed material information (?)
    - Add, delete, manipulate products
6. Environmental impact calculation
    - Calculate total environmental impact for a product based on its materials
    - Implement at least two calculation strategies
    - The strategy must be replaceable without modifying the Product class (Strategy pattern required)
7. Recycling guidance
    - Provide recycling guidance based on the product’s material composition
    - Handle mixed-material products in a reasonable and documented way
8. Product and Material registry (seperated)
    - Hold Products / Material
9. Product and Material storage
    - Store Products and Materials long term
---

### Non-functional requirements

- Layered architecture with at minimum: Presentation, Application and Domain
- Clear separation between UI and business logic
- Strategy pattern implemented and explained in the documentation
- JUnit tests for core domain logic (no console/UI code in tests)
- Continuous Integration that builds the project and runs all tests automatically
- Professional Git workflow with feature branches and meaningful commits
- Documentation: README, UML class diagram, and at least one sequence diagram



















