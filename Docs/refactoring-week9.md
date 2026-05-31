# Refactoring - Week 9
During this week we refactored: Domain entities, UI usage of entities, rewrote Material UI
### Entities
#### ProductEntity
Before the refactor, there was no error handling or preventing invalid states inside the constructor.
We added the error handling to the setter-methods and switched to using those.
```
this.name = name; // no error handling
    |                |
    v                v
SetName(name); // with error handling
```
#### MaterialEntity
Also updated the setter-methods; However we did not implement those into the constructor until later.
### Records
The UI's used to handle domain entities in order to display or update them. 
Now records are used rather than entities to separate Presentation from Domain.
```
List<Material> materials = this.materialService.RetrieveAll();
      |    | 
      v    v 
List<MaterialRecord> materials = this.materialService.RetrieveAll();
```
This was done for the materials and products. 
Furthermore, the services also got refactored to map from entities to records which the UI can utilize and display.

### Material UI rewrite
Due to the impact calculation strategies, material had to change a few times during development. 
This meant that the MaterialCRUDUI also had to change everytime as it was statically dependent on MaterialEntity.
As this is not feasible long-term, MaterialCRUDUI was rewritten to utilize a new helper class 
that would fetch the list of fields from MaterialEntity dynamically making it automatically adapt to changes to MaterialEntity.
```
String name = inputHandler.GetInput(String.class, "What's the materials name?");
float mass = inputHandler.GetInput(Float.class, "What's the mass of the material");
...
|   |
v   v
requestBuilder.CreateRecord();
```