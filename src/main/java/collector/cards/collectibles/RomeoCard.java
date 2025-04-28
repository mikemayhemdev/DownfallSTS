package collector.cards.collectibles;

import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.banditBoost;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class RomeoCard extends AbstractCollectibleCard {
    public final static String ID = makeID(RomeoCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 5, 3

    public RomeoCard() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FreeAttackPower(p, 1));
        applyToSelf(new VigorPower(p, magicNumber));
        if (banditBoost(1)) atb(new DrawCardAction(1));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }


    @Override
    public void triggerOnGlowCheck() {
        if (banditBoost(1)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            return;
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}