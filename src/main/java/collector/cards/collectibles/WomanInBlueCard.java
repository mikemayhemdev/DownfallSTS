package collector.cards.collectibles;

import collector.powers.collectioncards.WomanInBlueCardPower;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class WomanInBlueCard extends AbstractCollectibleCard {
    public final static String ID = makeID(WomanInBlueCard.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public WomanInBlueCard() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        tags.add(CardTags.HEALING);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        applyToSelf(new WomanInBlueCardPower());
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}