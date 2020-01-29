package ir.shayandaneshvar.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class Text {
    private final StringProperty text = new SimpleStringProperty();

    public Text(final String string) {
        this.text.set(string);
    }

    public String getText() {
        return text.get();
    }

    public void setText(final String string) {
        text.set(string);
    }

    public StringProperty getTextProperty() {
        return text;
    }
}
