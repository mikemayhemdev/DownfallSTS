package champ.cards;

import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.GladiatorStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VampiricStrike extends AbstractChampCard {

    public final static String ID = makeID("VampiricStrike");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 5;

    public VampiricStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.TECHNIQUE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (upgraded || p.stance instanceof BerserkerStance) {
            return super.canUse(p, m);
        }
        cantUseMessage = "I'm not in that Stance.";
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();

        atb(new VampireDamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        atb(new VampireDamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (!upgraded && bcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        upgradeDamage(2);
    }
}