package collector.cards.collectibles;

import collector.powers.NextTurnReservePower;
import collector.powers.NextTurnVigorPower;
import collector.powers.collectioncards.GremlinGangPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.*;
import static collector.util.Wiz.applyToSelf;

public class GremlinWizardCard extends AbstractCollectibleCard {
    public final static String ID = makeID(GremlinWizardCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 10, 5

    public GremlinWizardCard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(GREMLINGANG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new NextTurnVigorPower(magicNumber));
        applyToSelf(new NextTurnReservePower(1));
        applyToSelf(new GremlinGangPower(this));
    }

    public void upp() {
        upgradeMagicNumber(4);
    }


    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(GremlinGangPower.POWER_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            return;
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}