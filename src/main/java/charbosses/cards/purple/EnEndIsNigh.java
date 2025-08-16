package charbosses.cards.purple;

import basemod.ReflectionHacks;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.IroncladStatusPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;

import java.util.Iterator;

import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class EnEndIsNigh extends AbstractBossCard {
    public static final String ID = "downfall:EndIsNigh";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnEndIsNigh() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        this.loadJokeCardImage();
        baseMagicNumber = magicNumber = 200;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                    att(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            m.increaseMaxHp(magicNumber, true);
                        }
                    });

            }
        });

        Iterator var4 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var4.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var4.next();
            this.addToBot(new ApplyPowerAction(mo, m, new RitualPower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
        }
        this.addToBot(new ApplyPowerAction(p, m, new RitualPower(p, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnEndIsNigh();
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
