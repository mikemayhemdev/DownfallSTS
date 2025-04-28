package collector.cards.collectibles;

import collector.powers.collectioncards.GremlinGangPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.GREMLINGANG;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class ShieldGremlinCard extends AbstractCollectibleCard {
    public final static String ID = makeID(ShieldGremlinCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , 9, 3, , 

    public ShieldGremlinCard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(GREMLINGANG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new GremlinGangPower(this));
    }

    public void upp() {
        upgradeBlock(3);
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