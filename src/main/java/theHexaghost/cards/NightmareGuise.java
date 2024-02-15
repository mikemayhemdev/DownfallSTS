package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class NightmareGuise extends AbstractHexaCard {

    public final static String ID = makeID("NightmareGuise");

    public NightmareGuise() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseBlock = 4;
        isEthereal = true;
        cardsToPreview = new ShadowGuise();
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "NightmareGuise.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        superFlash(Color.PURPLE);
        AbstractCard q = new ShadowGuise(this);
        if (upgraded) q.upgrade();
        atb(new MakeTempCardInHandAction(q));
    }

    @Override
    public void afterlife() {
        blck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2);
            cardsToPreview.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}