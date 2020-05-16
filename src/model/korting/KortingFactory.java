package model.korting;

import model.ModelException;

import java.util.ArrayList;

/**
 * @author Pieter Herremans
 */

public class KortingFactory {

    public KortingStrategy KortingFactory(KortingEnum typeKorting, ArrayList<Object> args){
        KortingStrategy korting = null;

        switch (typeKorting){
            case DUURSTEKORTING:
                if(args.size() != 1) throw new ModelException("More then one or no args");
                korting = new Duurstekorting(Double.parseDouble((String)args.get(0)));
                break;
            case DREMPELKORTING:
                if(args.size() != 1) throw new ModelException("More then one or no args");
                korting = new Drempelkorting(Double.parseDouble((String)args.get(0)), Double.parseDouble((String)args.get(1)));
                break;
            case GROEPSKORTING:
                if(args.size() != 1) throw new ModelException("More then one or no args");
                korting = new Groepkorting(Double.parseDouble((String)args.get(0)), (String)args.get(1));
                break;
        }
        return korting;
    }
}
