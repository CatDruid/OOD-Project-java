[![Java CI](https://github.com/CatDruid/OOD-Project-java/actions/workflows/ci.yml/badge.svg)](https://github.com/CatDruid/OOD-Project-java/actions/workflows/ci.yml)
# OOD-Project-java

Sustainable Product and Recycling Management System - SPaRMS

SPaRMS gives users the ability to manage products with a focus on environmental sustainability. \
It can calculate the environmental impact on a product, taking every material used into consideration, 
as well as being able to utilize different calculation strategies e.g., simple sum or weighted by lifespan. \
Recycling guidance can also be given in accordance with the Swedish Waste Management.

<!-- TOC -->
* [OOD-Project-java](#ood-project-java)
  * [Build and Run instructions](#build-and-run-instructions)
    * [Tests](#tests)
  * [Architecture](#architecture)
    * [Flow of dependencies](#flow-of-dependencies)
    * [Isolation of Domain](#isolation-of-domain)
    * [Motivation](#motivation)
      * [Why 4 layers](#why-4-layers)
    * [Package Structure](#package-structure)
  * [Strategy pattern](#strategy-pattern)
    * [Problem](#problem)
    * [Solution](#solution)
    * [Benefits](#benefits)
  * [Technical Debt](#technical-debt-)
      * [Error Handling](#error-handling)
      * [Product UI](#product-ui)
      * [Persistency Layer issues](#persistency-layer-issues)
  * [Doc references and Diagrams](#doc-references-and-diagrams)
    * [Refactoring Week 9](#refactoring-week-9)
    * [UML Diagram](#uml-diagram)
    * [Sequence Diagram](#sequence-diagram)
    * [Other documentation files](#other-documentation-files)
  * [Team](#team)
  * [Branch naming](#branch-naming)
      * [Features](#features)
      * [Fixes, rewrites and refactors](#fixes-rewrites-and-refactors)
      * [Other branches](#other-branches)
<!-- TOC -->




## Build and Run instructions
First clone the repository with `git clone https://github.com/CatDruid/OOD-Project-java.git` \
Then move into the folder `cd path/OOD-Project-java` \
From there first build the project with `.\gradlew build` \
And then you can run it using `.\gradlew run`
<details>
<summary>command lines</summary>

```
git clone https://github.com/CatDruid/OOD-Project-java.git

cd path/OOD-Project-java

.\gradlew build
.\gradlew run
```
</details>

<details>
<summary>Run a cleaned up version</summary>

To run it clean use 
```
.\gradlew run --console plain -q
```
</details>


### Tests
To run the tests use the command
```
.\gradlew test
```
It will give you an index.html file which will display the results of the tests in detail.

## Architecture
For this project we chose a layered architecture consisting of these four layers: Presentation, Application, Domain, and Infrastructure.

The presentation layer is responsible for the input and output of the application.
This includes proper formatting as well as forwarding the users requests.

These requests are forwarded to the application layer, which coordinates them and calls upon the Domain layer.
The classes inside the application layer are mostly services that manage their respective domain entities.
For example, the Material and Product services are used for creating, retrieving, updating, and deleting material and product objects.

Inside the Domain resides the business logic of this system. E.g., the formulas for calculating the environmental impact are stored here inside classes.
Other entities inside take care of storing states, as
containers holding information and being responsible for invariant states.

The final fourth layer, the Infrastructure, is in charge of persistency,
meaning the storing and retrieving of data long term.
In concrete terms, this includes repositories, handling JSON files, or a database,
as well as registries acting as memory, storing data during the runtime of the application.

### Flow of dependencies
Due to this layered structure, there is a clear direction of dependencies, which is downward/inward. 
The lower layers know nothing about the layers above them; 
Application knows about Domain, but Domain knows nothing about Application. 
Responsibilities also cannot skip layers, meaning Presentation never directly interacts with Domain but only through Application.
The flow of dependencies looks like this based on the attributes described:
```
Presentation => Application => Domain
```
This, however, does not include infrastructure.
If you look at its behavior, you can see it interacts with Domain and application.
It stores Domain objects and Application interacts with it to retrieve those objects, meaning it exists in between these two layers.
Therefore, there is an alternative flow of dependency if Infrastructure is included.
```
Presentation => Application =====================> Domain                 
                          \\==> infrastructure ==>//
```

### Isolation of Domain
This flow of dependency makes it so that Domain is completely isolated 
and therefore does not depend on any other layer. Resulting in better modularity for expansion 
and modification, but more importantly, testability.

### Motivation
We chose a layered architecture for this project due to its simplicity in contrast to its benefits.
Layering the application leads to a clear flow of dependencies as described above as well as better testability.
#### Why 4 layers
The first layer, Presentation, comes intuitively, as a split between the UI and logic behind it is natural for a clear structure.
The reason why we split Domain and Application is that we needed services to manage the entities, i.e., Product and Material.
These services, however, should be separated from the Domain entities to ensure testability for the Domain entities.
Furthermore, the services and entities have different kinds of responsibilities, so splitting them is the most logical step. \
We also knew that we needed some kind of long-term storage for the entities, which resulted in the infrastructure layer. \
Additionally, these layers also make it easier to split the workload among the group, as there are clear sections that each person can work on.

### Package Structure
The package structure of the project reflects these layers but also includes subpackages for better structural integrity.
```
org.ood
    ├─ presentation
    │   ├─ Helpers
    │   └─ Records
    │       ├─ EntityRecords
    │       └─ Results
    ├─ application    
    ├─ infrastructure
    │   ├─ registries
    │   └─ repositories
    ├─ domain
    │   ├─ entities
    │   └─ impactStrategy
    └ Main
    
```
Classes that do not directly fall under a subpackage simply live inside the package of their respective layer.

## Strategy pattern
A requirement for this project was that there should be the option
to choose different formulas to calculate the environmental impact. Due to this, a problem arose.

### Problem
When implementing different calculation strategies, you also need to update the options displayed
to the user as well as the coordination inside the service mapping the user's choice to the correct strategy.
If this is done via a switch statement inside the service and hardcoded inside the UI, it leads to large OCP violations.
This is because if you now add a new strategy, you need to touch three layers and make many modifications to already existing code.

### Solution
To solve this problem, a design pattern is needed. Each strategy now lives inside its own
class implementing the interface 'ImpactCalculationStrategy'.
This interface is used inside the 'EnvironmentalImpactService,' which holds a strategy inside a private field that gets injected using the constructor.

To create the service and inject it with the correct strategy, a factory is used.
The 'EnvironmentalFactory' holds a static list of all available strategies.
A list of the names of the strategies can be retrieved so the UI can display these to the user.
The factory can then create a service with the right strategy using the users input
and the service is then available to be called upon by the UI. \
For the implementation, see the [UML Diagram](#uml-diagram).

### Benefits
The design pattern leads to the displaying and choosing of a strategy to be dynamic,
meaning that only a list inside the factory has to be updated when adding new strategies.
This resolves the issue of having to modify a lot of code and improves the extensibility of the project greatly.
Another aspect that benefits from this is testability, as now each strategy can be tested individually.

## Technical Debt                     
Technical debt describes the future cost of non-optimal 
and hasty decisions made during development that can prove to be hindering later on. 
This can also be found in our project:

#### Error Handling
Error handling should be coherent and follow a certain pattern throughout the whole project,
making it easy to follow errors and crashes. \
However, in our project we did not decide on a specific contract.
Therefore, the error handling differs between throwing exceptions, returning null, or just falling back to standard values.
This makes debugging exponentially harder and more time-consuming. \
It will only get worse in the future as long as no standard is present that can be followed and maintained.

#### Product UI
Presentation and Domain should be clearly separated, and the one-way dependency should be kept to a minimum.
In the current state, it would be hard to change the attributes of ProductEntity.
The reason for that is the sequence of creating or updating a ProductEntity.
It is hardcoded and statically dependent on Product.
This could later cause heavier workloads, as touching Domain would mean you'd have to touch Presentation. \
To fix this, it would require fetching the list of attributes and their types from ProductEntity dynamically.

#### Persistency Layer issues
Currently, the data is being stored in two files: materials.json and products.json. These, in turn, follow the same structure as their entities.
The problem is that the approach of simply transforming the object into a JSON produces an issue with the one-to-many relationship
between product and material: a product entity has many material entities and holds them. \
When saved, no further handling is being done to process them; the materials a product has are saved alongside it.
This means that each material is stored multiple times: first once per material in materials.json, which is correct, but then it is duplicated within products.json. \
So it is that an application with 1000 products and 2 materials will thus have at least 1002 materials saved in the best-case scenario, and fixing this would require reworking how the products are being saved and then loaded within the Infrastructure layer.
## Doc references and Diagrams

### [Refactoring Week 9](Docs/refactoring-week9.md)

### [UML Diagram](Docs/Project.puml)
![Project](Docs/project.puml)

### [Sequence Diagram](Docs/Sequence%20Diagram%20-%20Get%20Guidance%20for%20Product.svg)
![Sequence Diagram - Get Guidance for Product.svg](Docs/Sequence%20Diagram%20-%20Get%20Guidance%20for%20Product.svg)

### [Other documentation files](Docs)

## Team
Dag Sandström - Product management dev

David Charlier - Environmental impact dev

Alessandro Antonio Pretti Marin - Material management dev

Shared - Recycling / Repo management / Docs / UI / Design

## Branch naming

#### Features
```
feature/category/name
```
#### Fixes, rewrites and refactors
```
type/category/date/name
```
#### Other branches
```
main -- For finished and working releases only
devolpment -- Preparation and integration before releases
docs -- Branch for documentation
```
















