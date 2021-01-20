package champ.cards;

import champ.ChampMod;
import champ.actions.DevastateAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Devastate extends AbstractChampCard {

    public final static String ID = makeID("Devastate");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 24;

    public Devastate() {
        super(ID, 5, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);

        this.baseDamage = DAMAGE;
        tags.add(ChampMod.FINISHER);

    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.hasTag(ChampMod.FINISHER) && c!=this) {
            updateCost(-1);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        finisher();
    }

    public void upp() {
        upgradeDamage(8);
    }
}