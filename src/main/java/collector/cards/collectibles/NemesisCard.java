package collector.cards.collectibles;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.getEnemies;

public class NemesisCard extends AbstractCollectibleCard {
    public final static String ID = makeID(NemesisCard.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , , 

    public NemesisCard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.getIntentBaseDmg() > -1) {
            applyToSelf(new IntangiblePlayerPower(p, 1));
        }
    }

    public void upp() {
        upgradeBaseCost(1);
    }

    @Override
    public void triggerOnGlowCheck() {
        for (AbstractMonster m : getEnemies()) {
            if (m.getIntentBaseDmg() > -1) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}