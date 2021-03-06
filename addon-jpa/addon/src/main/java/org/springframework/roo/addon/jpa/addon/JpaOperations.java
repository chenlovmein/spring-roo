package org.springframework.roo.addon.jpa.addon;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.roo.addon.jpa.addon.entity.JpaEntityMetadata.RelationInfo;
import org.springframework.roo.classpath.details.ClassOrInterfaceTypeDetails;
import org.springframework.roo.classpath.details.FieldMetadata;
import org.springframework.roo.classpath.details.annotations.AnnotationMetadataBuilder;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.project.Feature;
import org.springframework.roo.project.maven.Pom;

import java.util.List;
import java.util.SortedSet;

/**
 * Provides JPA configuration and entity operations.
 *
 * @author Ben Alex
 * @author Alan Stewart
 * @author Juan Carlos García
 * @since 1.0
 */
public interface JpaOperations extends Feature {

  /**
   * This method is responsible for managing all JPA related artifacts
   * (META-INF/persistence.xml, applicationContext.xml, database.properties
   * and the project pom.xml) for the specified module
   *
   * @param ormProvider the ORM provider selected (Hibernate, OpenJPA,
   *            EclipseLink)
   * @param database the database (HSQL, H2, MySql, etc)
   * @param module the module where to install the persistence
   * @param jndi the JNDI datasource
   * @param hostName the host name where the database is
   * @param databaseName the name of the database
   * @param userName the username to connect to the database
   * @param password the password to connect to the database
   * @param profile string with profile where current jpa persistence will be applied.
   * @param force boolean that forces configuration if exists some previous configuration
   */
  void configureJpa(OrmProvider ormProvider, JdbcDatabase database, Pom module, String jndi,
      String hostName, String databaseName, String userName, String password, String profile,
      boolean force);

  /**
   * Indicates whether JPA can be installed in the currently focused module.
   *
   * @return <code>false</code> if no module has the focus
   */
  boolean isJpaInstallationPossible();

  /**
   * Check if jpa is installed
   *
   * @return
   */
  boolean isJpaInstalled();

  /**
   * Creates a new JPA embeddable class.
   *
   * @param name the name of the embeddable class (required)
   * @param serializable whether the class implements
   *            {@link java.io.Serializable}
   */
  void newEmbeddableClass(JavaType name, boolean serializable);

  /**
   * Creates a new entity.
   *
   * @param name the entity name (required)
   * @param createAbstract indicates whether the entity will be an abstract
   *            class
   * @param superclass the super class of the entity
   * @param implementsType the interface to implement
   * @param annotations the entity's annotations
   */
  void newEntity(JavaType name, boolean createAbstract, JavaType superclass,
      JavaType implementsType, List<AnnotationMetadataBuilder> annotations);

  /**
   * Updates an existing embeddable class to a JPA identifier class.
   *
   * @param identifierType the identifier type
   * @param identifierField the identifier field name
   * @param identifierColumn the identifier column name
   */
  void updateEmbeddableToIdentifier(JavaType identifierType, String identifierField,
      String identifierColumn);

  /**
   * Deletes an existing entity from project.
   *
   * @param entity the JavaType representing the file to be deleted from project
   */
  void deleteEntity(JavaType entity);

  SortedSet<String> getDatabaseProperties(String profile);

  /**
   * Gets field of an entity which defines a composition relation which current entity
   * is the child part.
   *
   * @param entity
   * @return null if entity is not the part of a composition relation. Otherwise returns current entity field metadata and relation info (from parent)
   */
  Pair<FieldMetadata, RelationInfo> getFieldChildPartOfCompositionRelation(JavaType entity);

  /**
   * Gets field of an entity which defines a composition relation which current entity
   * is the child part.
   *
   * @param entity
   * @return null if entity is not the part of a composition relation. Otherwise returns current entity field metadata and relation info (from parent)
   */
  Pair<FieldMetadata, RelationInfo> getFieldChildPartOfCompositionRelation(
      ClassOrInterfaceTypeDetails entityCdi);

  /**
   * Add datasource dependency for testing purposes in a module with repository classes.
   * This method can be called when installing/changing persistence database or when
   * adding repositories to the project.
   *
   * @param repositoryModuleName the module name where the dependency should be added.
   * @param profile the profile used to obtain the datasource property from
   *    spring config file.
   * @param databaseConfigPrefix the database prefix used to find the right dependency
   *    in the configuration file. It could be null if called from repository commands.
   */
  void addDatabaseDependencyWithTestScope(String repositoryModuleName, String profile,
      String databaseConfigPrefix);

  /**
   * Gets field of an entity which current entity is the child part of a relation.
   *
   * @param entity
   * @return list returns current entity field metadata and relation info (from parent)
   */
  List<Pair<FieldMetadata, RelationInfo>> getFieldChildPartOfRelation(JavaType entity);

  /**
   * Gets field of an entity which current entity is the child part of a relation.
   *
   * @param entityCdi
   * @return list returns current entity field metadata and relation info (from parent)
   */
  List<Pair<FieldMetadata, RelationInfo>> getFieldChildPartOfRelation(
      ClassOrInterfaceTypeDetails entityCdi);
}
