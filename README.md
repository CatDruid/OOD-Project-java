# OOD-Project-java

Sustainable Product and Recycling Managenent System - SPaRMS

SPaRMS gives users the ability to manage products with a focus on environmental sustainability.
It calculates and gives tips on recycling for both single and mixed material products and gives an estimated calculation of the environmental impact. 

Build for Java 25.

## Roles
Dag Sandström - Product management dev 

David Charlier - Material management dev 

Alessandro Antonio Pretti Marin - Environmental impact dev 

Shared - Recycling / Repo management / Docs / UI / Design

## Project structure
The project is structured into 4 layers. The presentation layer, the application layer, the domain layer and the infrastructure layer. The presentation layer is responsible for formatting outputs and getting inputs from the user in the form of a menu. Where as the services inside the application layer function as a manager for the domain entities and a bridge between the presentation and business logic inside the domain. The domain will be the host for the core logic of the project such as the materials and products as well as the impact calculation strategies. Lastly, the infrastructure layer is responsible for mainly saving and loading data to and from a database or files for example.

## Strategy patterns
A strategy pattern is used for the different formulas for environmentalimpact-calculation. The formulas are realized in their own seperate classes which all implement a interface. A factory is used where all the applicable strategies are created. From there the presentation layer can request a dynamic list of all the strategies to display them for the user. The user's choice then goes to the factory which translates it to the correct strategy which is given to the services. Due to the usage of a factory and strategy pattern it is really easy to add new or strategies as the presentation and application layer are not touched at all with exception to the factory, where the change has to be added to a static list.

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



















