package charbosses.cards.hermit;

import basemod.ReflectionHacks;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.HermitConcentrateAdder;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
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
import hermit.HermitMod;
import hermit.characters.hermit;

public class EnHyperfocused extends AbstractBossCard {
    public static final String ID = "downfall:Hyperfocused";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnHyperfocused() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 1, cardStrings.DESCRIPTION, CardType.POWER, hermit.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new HermitConcentrateAdder(m)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnHyperfocused();
    }


}
