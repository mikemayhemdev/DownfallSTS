package charbosses.cards.blue;

import basemod.ReflectionHacks;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.DefectAttackVoidPower;
import charbosses.powers.bossmechanicpowers.IroncladStatusPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;

public class EnMurderbot extends AbstractBossCard {
    public static final String ID = "downfall:Murderbot";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnMurderbot() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        this.loadJokeCardImage();
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new DefectAttackVoidPower(m)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnMurderbot();
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
