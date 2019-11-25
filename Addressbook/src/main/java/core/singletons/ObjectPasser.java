package core.singletons;

import model.Contact;

// En singleton för att enkelt kunna skicka en kontakt mellan fönster.
// Används för tillfället för att få informationen från vald post när den ska ändras
public class ObjectPasser {
    private static ObjectPasser instance = null;
    public static Contact contact;

    private ObjectPasser() {}

    public static ObjectPasser getInstance() {
        if(instance == null)
            instance = new ObjectPasser();
        return instance;
    }
}
