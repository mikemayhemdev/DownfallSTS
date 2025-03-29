package collector.cards.collectibles;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class LivingWallCard extends AbstractCollectibleCard {
    public final static String ID = makeID(LivingWallCard.class.getSimpleName());
    // intellij stuff skil, self, uncommon, , , , , , 

    public LivingWallCard() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        isPyre();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard toGen = AbstractDungeon.returnTrulyRandomCardInCombat();
        toGen.upgrade();
        toGen.freeToPlayOnce = true;
        makeInHand(toGen);
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}