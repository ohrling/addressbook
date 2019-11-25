package core.singletons;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// En singleton vars enda uppgift är att visa ett meddelande längst nere i högra hörnet
// i rightlabel i mainfönstret. Tänk system.out.println
public class MessageContainer {
    private static MessageContainer instance = null;
    private static final StringProperty rightLabelMessage = new SimpleStringProperty();

    public static MessageContainer getInstance() {
        if(instance == null)
            instance = new MessageContainer();
        return instance;
    }

    public static void setRightLabelMessage(String update) {
        rightLabelMessage.set(update);
    }

    public static StringProperty getRightLabelMessage() {
        return rightLabelMessage;
    }
}
