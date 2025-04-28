package collector.cards.collectibles;

import collector.powers.collectioncards.GremlinGangPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.GREMLINGANG;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class MadGremlinCard extends AbstractCollectibleCard {
    public final static String ID = makeID(MadGremlinCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 2, 2

    public MadGremlinCard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(GREMLINGANG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new LoseStrengthPower(p, magicNumber));
        applyToSelf(new GremlinGangPower(this));
    }

    public void upp() {
        upgradeMagicNumber(2);
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