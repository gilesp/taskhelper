package uk.co.vurt.taskhelper.server.domain.definition;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class PageItem {

    @NotNull
    private String name;

    @NotNull
    private String label;

    private String value;
    
    @Enumerated
    private InputTypes type;
}
