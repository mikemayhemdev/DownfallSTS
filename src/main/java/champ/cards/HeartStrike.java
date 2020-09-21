package champ.cards;

import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HeartStrike extends AbstractChampCard {

    public final static String ID = makeID("HeartStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = -1;

    public HeartStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        finisher();
        if (p.hasPower(ResolvePower.POWER_ID)) {
            this.baseDamage = p.getPower(ResolvePower.POWER_ID).amount;
            this.calculateCardDamage(m);
            dmg(m, AbstractGameAction.AttackEffect.SMASH);
        }
        if (!upgraded || !bcombo()) {
            atb(new RemoveSpecificPowerAction(p, p, ResolvePower.POWER_ID));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (upgraded && bcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    //TODO: you know the drill, same old damage display deal. one of the most annoying things to do - find some way to abstract it so it's only needed to be done once and copied to all other cards maybe?

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}