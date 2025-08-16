package charbosses.cards.purple;

import basemod.ReflectionHacks;
import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.WatcherAngryPower;
import charbosses.powers.bossmechanicpowers.WatcherTrackerAttacksPower;
import charbosses.powers.bossmechanicpowers.WatcherTrackerSkillsPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;

public class EnSteady extends AbstractBossCard {
    public static final String ID = "downfall:Steady";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnSteady() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 0, cardStrings.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.MAGIC);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new WatcherAngryPower(m)));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSteady();
    }


}
