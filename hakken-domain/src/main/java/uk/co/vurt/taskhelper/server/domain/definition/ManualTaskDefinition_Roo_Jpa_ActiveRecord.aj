// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package uk.co.vurt.taskhelper.server.domain.definition;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import uk.co.vurt.taskhelper.server.domain.definition.StaticTaskDefinition;

privileged aspect ManualTaskDefinition_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager ManualTaskDefinition.entityManager;
    
    @Transactional
    public void ManualTaskDefinition.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ManualTaskDefinition.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            StaticTaskDefinition attached = StaticTaskDefinition.findManualTaskDefinition(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ManualTaskDefinition.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ManualTaskDefinition.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public StaticTaskDefinition ManualTaskDefinition.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        StaticTaskDefinition merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager ManualTaskDefinition.entityManager() {
        EntityManager em = new StaticTaskDefinition().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ManualTaskDefinition.countManualTaskDefinitions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ManualTaskDefinition o", Long.class).getSingleResult();
    }
    
    public static List<StaticTaskDefinition> ManualTaskDefinition.findAllManualTaskDefinitions() {
        return entityManager().createQuery("SELECT o FROM ManualTaskDefinition o", StaticTaskDefinition.class).getResultList();
    }
    
    public static StaticTaskDefinition ManualTaskDefinition.findManualTaskDefinition(java.lang.Long id) {
        if (id == null) return null;
        return entityManager().find(StaticTaskDefinition.class, id);
    }
    
    public static List<StaticTaskDefinition> ManualTaskDefinition.findManualTaskDefinitionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ManualTaskDefinition o", StaticTaskDefinition.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}