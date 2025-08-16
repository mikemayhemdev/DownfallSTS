package charbosses.cards.blue;

import basemod.ReflectionHacks;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.IroncladStatusPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;

public class EnTemptation extends AbstractBossCard {
    public static final String ID = "downfall:Temptation";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnTemptation() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        this.loadJokeCardImage();
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        for(int i = 0; i < this.magicNumber; ++i) {
            AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
            this.addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnTemptation();
    }

    public void loadJokeCardImage() {
        Texture cardTexture;
        cardTexture = hermit.util.TextureLoader.getTexture(this.assetUrl.replace("cards","betacards"));
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(this, AbstractCard.class, "jokePortrait", cardImg);
    }

}
