package agent.planningagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;

/**
 * Cet agent met a jour sa fonction de valeur avec value iteration et choisit
 * ses actions selon la politique calculee.
 *
 * @author laetitiamatignon
 *
 */
public class ValueIterationAgent extends PlanningValueAgent {

    /**
     * discount facteur
     */
    protected double gamma;
    private Map<Etat, Double> Values;
    //*** VOTRE CODE

    /**
     *
     * @param gamma
     * @param mdp
     */
    public ValueIterationAgent(double gamma, MDP mdp) {
        super(mdp);
        this.gamma = gamma;
        //*** VOTRE CODE
        Values = new HashMap<>();
    }

    public ValueIterationAgent(MDP mdp) {
        this(0.9, mdp);

    }

    /**
     *
     * Mise a jour de V: effectue UNE iteration de value iteration
     */
    @Override
    public void updateV() {
        //delta est utilise pour detecter la convergence de l'algorithme
        //lorsque l'on planifie jusqu'a convergence, on arrete les iterations lorsque
        //delta < epsilon 
        this.delta = 0.0;
        //*** VOTRE CODE
        double max = 0, current = 0, sum=0;
        Map<Etat, Double> proba;
        try {
            for(Etat e : getMdp().getEtatsAccessibles()){
                Values.put(e, null);
                for(Action a : getMdp().getActionsPossibles(e)){
                    proba = getMdp().getEtatTransitionProba(e, a);
                    for(Etat s : proba.keySet()){
                        
                    }
                    
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ValueIterationAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        List<Action> la = this.getPolitique(e);
        Random r = new Random();
        return la.get(r.nextInt(la.size()));
    }

    @Override
    public double getValeur(Etat _e) {
        if(this.Values.containsKey(_e)){
            return this.Values.get(_e);
        } else return 0.0;
    }
    
    /**
     * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
     * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si
     * aucune action n'est possible)
     */
    @Override
    public List<Action> getPolitique(Etat _e) {
        List<Action> l = new ArrayList<Action>();
        l = this.getMdp().getActionsPossibles(_e);
        

        //renvoie le argmax
        return l;

    }

    @Override
    public void reset() {
        super.reset();
        Values = null;
        this.notifyObs();

    }

    @Override
    public void setGamma(double arg0) {
        this.gamma = arg0;
    }
}
