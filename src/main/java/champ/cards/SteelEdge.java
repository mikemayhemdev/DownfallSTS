package champ.cards;

import automaton.actions.EasyXCostAction;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class SteelEdge extends AbstractChampCard {
    public final static String ID = makeID("SteelEdge");

    public SteelEdge() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 8;
        this.tags.add(ChampMod.FINISHER);
        postInit();
        loadJokeCardImage(this, "SteelEdge.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
                if ((i < effect-1) && (effect !=0)){
                    finisher(true);
                }
            }
            return true;
        }));
        finisher();
    }

    public void upp() {
        upgradeDamage(3);
    }
}