package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import gremlin.cards.BubbleBarrier;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class NemesisCard extends AbstractCollectibleCard {
    public final static String ID = makeID(NemesisCard.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , , 

    public NemesisCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        exhaust = true;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IntangiblePlayerPower(p, 1));
        atb(new MakeTempCardInDrawPileAction(new Burn(), magicNumber, true, true));
    }

    public void upp() {
        uDesc();
        upgradeMagicNumber(-1);
    }
}