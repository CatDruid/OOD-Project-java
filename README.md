# OOD-Project-java

Description placeholder


## Roles
Dag Sandström - TBD

David Charlier - TBD

Alessandro Antonio Pretti Marin - TBD


## Branch naming

#### Features:
```
feature/category/name
```
#### Fixes and refactors:
```
type/category/date/name
```


## Requirements

### Functional requirements

1. Product management
    - Attributes: name, category, estimated lifespan, and one or more materials
    - List registered products
    - View detailed product information
2. Material management
    - Attributes: name, environmental impact value, and recycling category/instruction
    - Materials must be reusable across multiple products
3. Environmental impact calculation
    - Calculate total environmental impact for a product based on its materials
    - Implement at least two calculation strategies
    - The strategy must be replaceable without modifying the Product class (Strategy pattern required)
4. Recycling guidance
    - Provide recycling guidance based on the product’s material composition
    - Handle mixed-material products in a reasonable and documented way

---

### Non-functional requirements

- Layered architecture with at minimum: Presentation, Application and Domain
- Clear separation between UI and business logic
- Strategy pattern implemented and explained in the documentation
- JUnit tests for core domain logic (no console/UI code in tests)
- Continuous Integration that builds the project and runs all tests automatically
- Professional Git workflow with feature branches and meaningful commits
- Documentation: README, UML class diagram, and at least one sequence diagram



















