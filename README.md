[![Java CI](https://github.com/CatDruid/OOD-Project-java/actions/workflows/ci.yml/badge.svg)](https://github.com/CatDruid/OOD-Project-java/actions/workflows/ci.yml)
# OOD-Project-java

Sustainable Product and Recycling Management System - SPaRMS

SPaRMS gives users the ability to manage products with a focus on environmental sustainability.
It calculates the environmental impact and gives tips on recycling for both single and mixed material products.

<!-- TOC -->
* [OOD-Project-java](#ood-project-java)
  * [Build and Run instructions](#build-and-run-instructions)
    * [Tests](#tests)
  * [Architecture](#architecture)
    * [Flow of dependencies](#flow-of-dependencies)
    * [Isolation of Domain](#isolation-of-domain)
    * [Motivation](#motivation)
    * [Package Structure](#package-structure)
  * [Strategy pattern](#strategy-pattern)
      * [Problem](#problem)
      * [Solution](#solution)
      * [Benefits](#benefits)
  * [UML Diagram](#uml-diagram)
  * [Sequence Diagram](#sequence-diagram)
  * [Roles](#roles)
  * [Branch naming](#branch-naming)
      * [Features:](#features)
      * [Fixes, rewrites and refactors:](#fixes-rewrites-and-refactors)
      * [Other branches:](#other-branches)
<!-- TOC -->




## Build and Run instructions
First clone the repository with `git clone https://github.com/CatDruid/OOD-Project-java.git`. \
Then move into the folder `cd path/OOD-Project-java`. \
From there first build the project with `.\gradlew build` \
and then you can run it using `.\gradlew run`.
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
<summary> Run a cleaned up version </summary>
To run it clean use `.\gradlew run --console plain -q`.
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

These requests are forwarded to the application layer which coordinates those and calls upon the Domain layer.
The classes inside the application layer are mostly services which manage their responsible domain entities respectively. 
For example, the Material and Product services are used for creating, retrieving, updating, and deleting material and product objects.

Inside the Domain resides the Business logic of this system. E.g. the formulas for calculating the environmental impact are stored here inside of classes.
Other Entities inside take care of storing states, as 
containers holding information, and being responsible for invariant states. 

The final fourth layer, the infrastructure, is in charge of persistency, 
meaning the storing and retrieving data long term.
In concrete terms, this includes repositories, handling JSON files or a database, 
as well as registries acting as memory, storing data during the runtime of the application.

### Flow of dependencies
Due to this layered structure there is a clear direction of dependencies, which is down-/inward. 
The lower layers know nothing about the layers above them; 
Application knows about Domain but Domain knows nothing about Application. 
Responsibilities are also not able to skip layers meaning Presentation doesn't ever directly interact with Domain, but only through Application.
The flow of dependencies looks like this based on the attributes described:
```
Presentation => Application => Domain
```
This, however, does not include infrastructure. 
If you look at its behavior you can see it interacts with Domain and application.
It stores Domain objects and Application interacts with it to retrieve those objects, meaning it exists in between these two layers.
Therefore, there is an alternative flow of dependency if infrastrucute is included.
```
Presentation => Application =====================> Domain                 
                          \\==> infrastructure ==>//
```

### Isolation of Domain
This flow of dependency makes it so, that Domain is completely isolated 
and therefore does not depend on any other layer. Resulting in better modularity for expansion 
and modification, but more importantly, testability.

### Motivation
We chose a layered architecture for this project due to its simplicity in contrast to its benefits.
Layering the application leads to clear flow of dependencies as described above as well as for better testability.
TODO WHY 4 LAYERS AND NOT 2
It also makes it easier to split the workload among the group as there are clear sections which each person can work on.

### Package Structure
The package structure of the project reflects these layers but also includes sub packages, for better structural integrity.
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
Classes which do not directly fall under a sub package, simply live inside the package of their respective layer.

## Strategy pattern
A requirement for this project was, that there should be the option 
to chose different formulas to calculate the environmental impact. Due to this a problem arose.
#### Problem
When implementing different calculation strategies, you also need to update the options displayed 
to the user as well as the coordination inside the service mapping the users choice to the correct strategy.
If this is done via a switch statement inside the service and hardcoded inside the UI, it leads to large OCP violations.
This is because if you now add a new strategy, you need to touch three layers and make many modifications to already existing code.
#### Solution
To solve this problem a design pattern is needed. Each strategy now lives inside its own 
class implementing the interface 'ImpactCalculationStrategy'.
This interface is used inside the 'EnvironmentalImpactService' which holds a strategy inside a private field which gets injected using the constructor.

To create the service and inject it with the correct strategy, a factory is used.
The 'EnvironmentalFactory' holds a static list of all available strategies.
A list of the names of the strategies can be retrieved so the UI can display these to the user. 
The factory can then create a service with the right strategy using the users input 
and the service is then available to be called upon by the UI.
#### Benefits
The design pattern leads to the displaying and choosing of a strategy to be dynamic,
meaning that only a list inside the factory has to be updated when adding new strategies.
This resolves the issue of having to modify a lot of code and improves the extensibility of the project greatly.
Another aspect that benefits from this is testability as now each strategy can be tested individually.


## UML Diagram
TODO link to UML

## Sequence Diagram
![Sequence Diagram - Get Guidance for Product.svg](Docs/Sequence%20Diagram%20-%20Get%20Guidance%20for%20Product.svg)

## Roles
Dag Sandström - Product management dev

David Charlier - Environmental impact dev

Alessandro Antonio Pretti Marin - Material management dev

Shared - Recycling / Repo management / Docs / UI / Design

## Branch naming

#### Features:
```
feature/category/name
```
#### Fixes, rewrites and refactors:
```
type/category/date/name
```
#### Other branches:
```
main - For finished and working releases only
devolpment -- Preparation and integration before releases
docs -- Branch for documentation
```
















