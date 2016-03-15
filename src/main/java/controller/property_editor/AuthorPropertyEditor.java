package controller.property_editor;

import domain.Author;
import service.AuthorService;

import java.beans.PropertyEditorSupport;

public class AuthorPropertyEditor extends PropertyEditorSupport{

    public AuthorPropertyEditor(AuthorService dbCurrencyManager) {
        this.dbCurrencyManager = dbCurrencyManager;
    }

    private AuthorService dbCurrencyManager;

    public String getAsText() {
        Author obj = (Author) getValue();
        if (null==obj) {
            return "";
        } else {
            return obj.getId().toString();
        }
    }

    public void setAsText(final String value) {
        try {
            Long id = Long.parseLong(value);
            Author author = dbCurrencyManager.get(id);
            if (null!= author) {
                super.setValue(author);
            } else {
                throw new IllegalArgumentException("Binding error. Cannot find author with id  ["+value+"]");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Binding error. Invalid id: " + value);
        }
    }

}

