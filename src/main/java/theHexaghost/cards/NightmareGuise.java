package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class NightmareGuise extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("NightmareGuise");

    public NightmareGuise() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
        isEthereal = true;
        cardsToPreview = new ShadowGuise();
        tags.add(HexaMod.AFTERLIFE);
        this.keywords.add(downfallMod.keywords_and_proper_names.get("afterlife"));
        HexaMod.loadJokeCardImage(this, "NightmareGuise.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        superFlash(Color.PURPLE);
        AbstractCard q = new ShadowGuise(this);
        atb(new MakeTempCardInHandAction(q));
    }

    @Override
    public void afterlife() {
        blck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}