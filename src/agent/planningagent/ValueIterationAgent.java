package agent.planningagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
import environnement.gridworld.ActionGridworld;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Cet agent met a jour sa fonction de valeur avec value iteration
 * et choisit ses actions selon la politique calculee.
 * @author laetitiamatignon
 *
 */
public class ValueIterationAgent extends PlanningValueAgent{
    /**
     * discount facteur
     */
    protected double gamma;
    private Map<Etat, Double> Values;
    
    
    
    /**
     *
     * @param gamma
     * @param mdp
     */
    public ValueIterationAgent(double gamma,MDP mdp) {
        super(mdp);
        this.gamma = gamma;
        Values = new HashMap<>();
        for(Etat e : getMdp().getEtatsAccessibles()){
            Values.putIfAbsent(e, 0.0);
        }
    }
    
    
    public ValueIterationAgent(MDP mdp) {
        this(0.9,mdp);
        
    }
    
    /**
     *
     * Mise a jour de V: effectue UNE iteration de value iteration
     */
    @Override
    public void updateV(){
        //delta est utilise pour detecter la convergence de l'algorithme
        //lorsque l'on planifie jusqu'a convergence, on arrete les iterations lorsque
        //delta < epsilon
        this.delta=0.0;
        //*** VOTRE CODE
        Double max = 0., min=0., current, max_max = 0., min_min = 0.;
        Map<Etat, Double> proba;
        Map<Etat, Double> temp = new HashMap<>();
        try {
            for(Etat e : getMdp().getEtatsAccessibles()){
                temp.putIfAbsent(e, 0.);
                if(!getMdp().estAbsorbant(e)){
                    for(Action a : getMdp().getActionsPossibles(e)){
                        current = 0.;
                        proba = getMdp().getEtatTransitionProba(e, a);
                        for(Etat s : proba.keySet()){
                            current = current + (proba.get(s) * (getMdp().getRecompense(e, a, s) + (gamma*Values.get(s))));
                            //System.out.println("current = " + current +"+ (" +proba.get(s)+" * (" +getMdp().getRecompense(e, a, s) + " + (" + gamma + " * " + this.getValeur(s)+ "))) = " + current);
                        }
                        if(current > max) max = current;
                        if(current<min) min = current;
                    }
                    temp.put(e, max);
                    if(max > max_max) max_max = max;
                    if(min< min_min)    min_min = min;
                    max = min = 0.;
                }
            }
            Values = temp;
        } catch (Exception ex) {
            Logger.getLogger(ValueIterationAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // mise a jour vmax et vmin pour affichage du gradient de couleur:
        //vmax est la valeur de max pour tout s de V
        //vmin est la valeur de min pour tout s de V
        // ...
        vmax = max_max;
        vmin = min_min;
        
        //******************* a laisser a la fin de la methode
        this.notifyObs();
    }
    
    
    /**
     * renvoi l'action executee par l'agent dans l'etat e
     */
    @Override
    public Action getAction(Etat e) {
        //*** VOTRE CODE
        return getMdp().estAbsorbant(e) ? null :  getPolitique(e).get(new Random().nextInt(this.getMdp().getActionsPossibles(e).size()));
    }
    @Override
    public double getValeur(Etat _e) {
        if(this.Values.containsKey(_e)){
            return this.Values.get(_e) != null ? this.Values.get(_e) : 0.;
        } else return 0.0;
    }
    
    /**
     * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
     * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si aucune action n'est possible)
     */
    @Override
    public List<Action> getPolitique(Etat _e) {
        List<Action> l = new ArrayList<>();
        //*** VOTRE CODE
        double max = 0.;
        List<Action> ac = getMdp().getActionsPossibles(_e);
        for(Action a : ac){
            try {
                for(Etat e : getMdp().getEtatTransitionProba(_e, a).keySet()){
                    l.add(a);
                    if (Values.get(e)>max || (getMdp().estAbsorbant(e) && getMdp().getRecompense(_e, a, e)>0)) {
                        max = Values.get(e);
                        l.clear();
                        l.add(a);
                    }else if (Values.get(e)<max || (getMdp().estAbsorbant(e) && getMdp().getRecompense(_e, a, e)<0)) {
                        l.remove(a);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ValueIterationAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return l;
    }
    
    @Override
    public void reset() {
        super.reset();
        Values = new HashMap<>();
        for(Etat e : getMdp().getEtatsAccessibles()){
            Values.putIfAbsent(e, 0.0);
        }        this.notifyObs();
    }
    
    
    @Override
    public void setGamma(double arg0) {
        this.gamma = arg0;
    }
    
    
}
