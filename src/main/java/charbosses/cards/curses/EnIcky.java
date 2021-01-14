package charbosses.cards.curses;

import downfall.util.TextureLoader;
import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.status.EnSlimed;
import charbosses.cards.status.EnWound;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import slimebound.SlimeboundMod;

public class EnIcky extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Icky";
    private static final CardStrings cardStrings;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/icky.png");

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("downfall:Icky");
    }

    public EnIcky() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE, AbstractMonster.Intent.MAGIC);
        this.exhaust = true;
        portrait = TextureLoader.getTextureAsAtlasRegion(IMG_PATH);
        portraitImg = TextureLoader.getTexture(IMG_PATH);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyMakeTempCardInHandAction(new EnSlimed(), 1));
        this.addToBot(new MakeTempCardInDiscardAction(new Slimed(), 1));
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnIcky();
    }
}
