package model.korting;

import model.ModelException;

import java.util.ArrayList;

/**
 * @author Pieter Herremans, Vanhaeren Corentin
 */

public class KortingFactory {

    public KortingStrategy kortingFactory(KortingEnum typeKorting, ArrayList<Object> args){
        KortingStrategy korting = null;
        //slot1: percentage korting, slot2: drempelbedrag korting, slot3: groep korting
        switch (typeKorting){
            case GEEN:
                //if(args.size() != 1) throw new ModelException("More then one or no args");
                //korting = new Duurstekorting(Double.parseDouble((String)args.get(0)));
                korting = new Geenkorting();
                break;
            case DUURSTEKORTING:
                //if(args.size() != 1) throw new ModelException("More then one or no args");
                //korting = new Duurstekorting(Double.parseDouble((String)args.get(0)));
                korting = new Duurstekorting((Double) args.get(0));
                break;
            case DREMPELKORTING:
                //if(args.size() != 1) throw new ModelException("More then one or no args");
                //korting = new Drempelkorting(Double.parseDouble((String)args.get(0)), Double.parseDouble((String)args.get(1)));
                korting = new Drempelkorting((Double)args.get(0), (Double)args.get(1));
                break;
            case GROEPKORTING:
                //if(args.size() != 1) throw new ModelException("More then one or no args");
                //korting = new Groepkorting(Double.parseDouble((String)args.get(0)), (String)args.get(2));
                korting = new Groepkorting((Double)args.get(0), (String)args.get(2));
                break;
        }
        //System.out.println(korting + " kortingfactory");
        //System.out.println(args + " print args");
        return korting;
    }
}