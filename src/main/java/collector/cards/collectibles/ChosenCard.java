package collector.cards.collectibles;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.getEnemies;

public class ChosenCard extends AbstractCollectibleCard {
    public final static String ID = makeID(ChosenCard.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , 3, 2

    public ChosenCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.getIntentBaseDmg() <= -1) {
            applyToEnemy(m, new WeakPower(m, magicNumber, false));
            applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        }
    }

    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public void triggerOnGlowCheck() {
        for (AbstractMonster m : getEnemies()) {
            if (m.getIntentBaseDmg() <= -1) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}