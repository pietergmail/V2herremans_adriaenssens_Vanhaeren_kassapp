package model.kassabon;

/**
 * @author Vanhaeren Corentin
 */

public abstract class KassabonDecorator implements Component {
    private Component wrappee;

    public KassabonDecorator(Component component){
        this.wrappee = component;
    }

    @Override
    public String kassabon(){
        return wrappee.kassabon();
    };
}
