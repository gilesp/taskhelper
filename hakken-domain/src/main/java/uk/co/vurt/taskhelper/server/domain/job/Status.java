package uk.co.vurt.taskhelper.server.domain.job;

import org.springframework.roo.addon.json.RooJson;

@RooJson
public enum Status {

    NEW, INPROGRESS, COMPLETED;
}
