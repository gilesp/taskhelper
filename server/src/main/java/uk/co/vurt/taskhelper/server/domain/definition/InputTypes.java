package uk.co.vurt.taskhelper.server.domain.definition;

import org.springframework.roo.addon.json.RooJson;

@RooJson
public enum InputTypes {

    LABEL, TEXT, DIGITS, DATETIME;
}
