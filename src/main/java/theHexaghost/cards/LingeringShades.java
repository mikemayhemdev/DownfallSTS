package theHexaghost.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.actions.AllEtherealToHandAction;
import theHexaghost.actions.RetractAction;

public class LingeringShades extends AbstractHexaCard{

    public final static String ID = makeID("LingeringShades");

    public LingeringShades() {
        super(ID, 1, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        baseBurn = burn = 11;
        HexaMod.loadJokeCardImage(this, "LingeringShades.png");
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RetractAction());
        burn(m, burn);
        atb(new AllEtherealToHandAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(5);
        }
    }

    @Override
    public float getTitleFontSize() {
        if(Settings.language != Settings.GameLanguage.ENG) {
            return 19.0F;
        } else {
            return 23.0F;
        }
    }

}
