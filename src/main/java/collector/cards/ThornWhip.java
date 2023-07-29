package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.getEnemies;
import static collector.util.Wiz.isAfflicted;

public class ThornWhip extends AbstractCollectorCard {
    public final static String ID = makeID(ThornWhip.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 11, 1, , , , 

    public ThornWhip() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (isAfflicted(m)) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        }
    }

    public void upp() {
        upgradeDamage(3);
        uDesc();
    }

    @Override
    public void triggerOnGlowCheck() {
        for (AbstractMonster m : getEnemies()) {
            if (m.hasPower(VulnerablePower.POWER_ID) && m.hasPower(WeakPower.POWER_ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}