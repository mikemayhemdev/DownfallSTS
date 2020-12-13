/*
package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinalRevenge extends AbstractChampCard {

    public final static String ID = makeID("FinalRevenge");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = -1;

    public FinalRevenge() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        this.baseDamage = (p.maxHealth - p.currentHealth);
        this.calculateCardDamage(m);
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        if (upgraded && bcombo()) exhaust = false;
        finisher();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (bcombo() && upgraded) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
*/