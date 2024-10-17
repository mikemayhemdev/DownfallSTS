package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;

public class SeventhEye extends AbstractHexaCard {

    public final static String ID = makeID("SeventhEye");

    public SeventhEye() {
        super(ID, 1, AbstractCard.CardType.SKILL, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.exhaust = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "SeventhEye.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new BetterDrawPileToHandAction(1));
        atb(new RandomFlameAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
