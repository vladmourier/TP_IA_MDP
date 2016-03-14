package agent.planningagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
import environnement.gridworld.ActionGridworld;
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
    private List<Etat> visited;
    //*** VOTRE CODE
    
    
    
    /**
     *
     * @param gamma
     * @param mdp
     */
    public ValueIterationAgent(double gamma,MDP mdp) {
        super(mdp);
        this.gamma = gamma;
        //*** VOTRE CODE
        visited = new ArrayList<>();
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
        
        
        
        
        // mise a jour vmax et vmin pour affichage du gradient de couleur:
        //vmax est la valeur de max pour tout s de V
        //vmin est la valeur de min pour tout s de V
        // ...
        
        //******************* a laisser a la fin de la methode
        this.notifyObs();
    }
    
    
    /**
     * renvoi l'action executee par l'agent dans l'etat e
     */
    @Override
    public Action getAction(Etat e) {
        //*** VOTRE CODE
        
        
        return null;
    }
    @Override
    public double getValeur(Etat _e) {
        //*** VOTRE CODE
        double res = 0, proba, recompense;
        if(!visited.contains(_e)){
            visited.add(_e);
            for(Action a : this.getMdp().getActionsPossibles(_e)){
                try {
                    Map<Etat, Double> probabilites = this.getMdp().getEtatTransitionProba(_e,a);
                    for(Etat e : probabilites.keySet()){
                        proba = probabilites.get(e);
                        recompense = this.getMdp().getRecompense(_e, a, e);
                        res += proba * (recompense + (gamma * this.getValeur(e)));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(AgentRandom.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } else {
            
        }
        
        return res;
    }
    /**
     * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
     * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si aucune action n'est possible)
     */
    @Override
    public List<Action> getPolitique(Etat _e) {
        List<Action> l = new ArrayList<>();
        //*** VOTRE CODE
        
        
        return l;
        
    }
    
    @Override
    public void reset() {
        super.reset();
        //*** VOTRE CODE
        
        
        
        
        
        /*-----------------*/
        this.notifyObs();
        
    }
    
    
    @Override
    public void setGamma(double arg0) {
        this.gamma = arg0;
    }
    
    
}
