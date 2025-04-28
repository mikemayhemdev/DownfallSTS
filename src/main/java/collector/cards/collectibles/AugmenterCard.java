package collector.cards.collectibles;

import basemod.helpers.CardModifierManager;
import collector.powers.AugmenterPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.JAX;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import expansioncontent.cardmods.EtherealMod;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class AugmenterCard extends AbstractCollectibleCard {
    public final static String ID = makeID(AugmenterCard.class.getSimpleName());
    // intellij stuff power, self, uncommkon, , , , , 3, 2

    public AugmenterCard() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);

        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        cardsToPreview = new JAX();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = new JAX();
        CardModifierManager.addModifier(c, new EtherealMod());
        atb(new MakeTempCardInHandAction(c));
        applyToSelf(new AugmenterPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}