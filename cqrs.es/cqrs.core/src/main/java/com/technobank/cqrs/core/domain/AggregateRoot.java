package com.technobank.cqrs.core.domain;

import com.technobank.cqrs.core.event.BaseEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 Entidad dentro del aggregate* que es responsable de mantener el estado consistente.

 * Entidad o grupos de entidades que siempre mantienen un estado consistente.

 Aggregate es perfecto para implementar un modelo de comando en cualquier aplicación CQRS

 */
public abstract class AggregateRoot {

    protected String id;
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger LOG = Logger.getLogger(AggregateRoot.class.getName());

    public List<BaseEvent> getUncommittedChanges(){
        return this.changes;
    }

    public void markChangesAsCommitted(){
        this.changes.clear();
    }

    protected void applyChange(BaseEvent baseEvent, Boolean isNewEvent){
        try {
            var method = getClass().getDeclaredMethod("apply",baseEvent.getClass());
            method.setAccessible(true);
            method.invoke(this,baseEvent);
        } catch (NoSuchMethodException e) {
            LOG.log(Level.WARNING, MessageFormat.format("El método Apply no fue encontrado en el aggregate para {0} ",
                    baseEvent.getClass().getName()));
        }catch (Exception e){
            LOG.log(Level.SEVERE, "Error 'aplicando ó applying' evento al Aggregate ",e);
        }finally {
            if(isNewEvent){
                changes.add(baseEvent);
            }
        }
    }

    public void raiseEvent(BaseEvent baseEvent){
        applyChange(baseEvent,true);
    }

    public void replayEvents(Iterable<BaseEvent> events){
        events.forEach(event-> applyChange(event,false));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
