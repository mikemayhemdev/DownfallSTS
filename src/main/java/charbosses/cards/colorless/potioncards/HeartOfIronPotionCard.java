package charbosses.cards.colorless.potioncards;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import downfall.util.TextureLoader;
import downfall.vfx.PotionThrowEffect;
import expansioncontent.expansionContentMod;

public class HeartOfIronPotionCard extends AbstractBossCard {
    public static final String ID = "downfall:HeartOfIronPotionCard";
    private static final PotionStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getPotionString("HeartOfIron");
    }
    public HeartOfIronPotionCard() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 0, "", CardType.SKILL, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        rawDescription = getCorrectedText();
        initializeDescription();
        initializeDescriptionCN();
    }


    public String getCorrectedText(){
        String temp = cardStrings.DESCRIPTIONS[0] + 6 + cardStrings.DESCRIPTIONS[1];
        temp = temp.replace("#b", "");
        temp = temp.replace("#y", "");
        return temp;
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/HeartOfIron.png", m.hb.cX, m.hb.cY, m.hb.cX, m.hb.cY, 2F, 0.6F, false, true), 0.6F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MetallicizePower(m, 4), 4));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeartOfIronPotionCard();
    }


}
