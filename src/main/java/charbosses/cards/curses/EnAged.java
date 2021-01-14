package charbosses.cards.curses;

import downfall.util.TextureLoader;
import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.status.EnSlimed;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import guardian.GuardianMod;
import slimebound.SlimeboundMod;

public class EnAged extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Aged";
    private static final CardStrings cardStrings;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/aged.png");

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("downfall:Aged");
    }

    public EnAged() {
        super(ID, cardStrings.NAME, IMG_PATH, -2, cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE, AbstractMonster.Intent.MAGIC);
        this.isEthereal = true;
        portrait = TextureLoader.getTextureAsAtlasRegion(IMG_PATH);
        portraitImg = TextureLoader.getTexture(IMG_PATH);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true));
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnAged();
    }
}
