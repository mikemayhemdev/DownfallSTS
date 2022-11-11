package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class PreciseThrust extends AbstractChampCard {

    public final static String ID = makeID("PreciseThrust");

    //stupid intellij stuff attack, self_and_enemy, uncommon

    private static final int DAMAGE = 8;

    public PreciseThrust() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (p.stance.ID.equals(NeutralStance.STANCE_ID)) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (nostance()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
    }
}