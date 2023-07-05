package collector.cards.collectibles;

import collector.cards.AbstractCollectorCard;
import collector.powers.collectioncards.AcidSlimeCardPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class AcidSlimeCard extends AbstractCollectibleCard {
    public final static String ID = makeID(AcidSlimeCard.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , , 

    public AcidSlimeCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AcidSlimeCardPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}