# Welcome to this tutorial on Spring JPA

This Demo has developed to show features and abilities of Jpa
In Real Projects.

[Query Projection](#query-projection)

## Query Projection

### What is projection
In Relational algebra, projection means collecting a subset of columns for use in operations, i.e. a projection is  <br> 
the list of columns selected. because if the all data is fetched causes a bottleneck and waste your resources and time.

### Types of Projections Supported by Spring Data JPA

Based on JPA’s query capabilities, Spring Data JPA gives you several options for defining your use case’s perfect projection. You can:
1. Use a scalar projection that consists of one or more database columns that are returned as an Object[]. This projection provides great performance for the read operation but it is used rarely. This is because DTO projections offer the same benefits while being much easier to use.
2. Use a DTO projection, which selects a custom set of database columns. It uses them to call a constructor and returns one or more unmanaged objects. This is a great projection if you don’t need to change the selected data.
3. Use an entity projection that selects all database columns mapped by an entity class and returns them as a managed object. Entities are the recommended projection if you want to change the retrieved information.

for more detail please read [Spring Data JPA: Query Projections](https://thorben-janssen.com/spring-data-jpa-query-projections/)

### In this Tutorial
In this tutorial, the projection portion into two approach
1. projection on Repository interface
2. projection on native Query and use ```SqlResultSetMapping```

for type one, you can read the PersonRepository interface in ```queryprojection``` package.
Also for another approach you can see AuthorService and Author classes in ```resultsetmapping``` package. 