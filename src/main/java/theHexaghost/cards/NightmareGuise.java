package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NightmareGuise extends AbstractHexaCard {

    public final static String ID = makeID("NightmareGuise");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 9;
    private static final int UPG_BLOCK = 4;

    public NightmareGuise() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        isEthereal = true;
        cardsToPreview = new ShadowGuise();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void afterlife() {
        superFlash(Color.PURPLE);
        AbstractCard q = new ShadowGuise(this);
        if (upgraded) q.upgrade();
        atb(new MakeTempCardInHandAction(q));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            cardsToPreview.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}