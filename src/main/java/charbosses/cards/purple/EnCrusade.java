package charbosses.cards.purple;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.CharBossWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.WatcherDivinityNoRemovePower;
import charbosses.powers.cardpowers.EnemyMantraPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;
import hermit.util.Wiz;

public class EnCrusade extends AbstractBossCard {
    public static final String ID = "downfall:Crusade";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnCrusade() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        this.loadJokeCardImage();
        baseMagicNumber = magicNumber = 10;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        if (m.hasPower(EnemyMantraPower.POWER_ID)){
           int amount = m.getPower(EnemyMantraPower.POWER_ID).amount;
            Wiz.atb(new HealAction(m, m, amount * magicNumber));
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(m));
            Wiz.atb(new ApplyPowerAction(m, m, new StrengthPower(m, amount), amount));
            Wiz.atb(new RemoveSpecificPowerAction(m, m, m.getPower(EnemyMantraPower.POWER_ID)));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCrusade();
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
