package charbosses.cards.colorless.potioncards;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;
import downfall.vfx.PotionThrowEffect;
import expansioncontent.expansionContentMod;

public class DuplicationPotionCard extends AbstractBossCard {
    public static final String ID = "downfall:DuplicationPotionCard";
    private static final PotionStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getPotionString("DuplicationPotion");
    }

    public DuplicationPotionCard() {
        super(ID, cardStrings.NAME, expansionContentMod.makeCardPath("SummonMushrooms.png"), 0, "", CardType.SKILL, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        portrait = TextureLoader.getTextureAsAtlasRegion(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        portraitImg = TextureLoader.getTexture(expansionContentMod.makeCardPath("SummonMushrooms.png"));
        rawDescription = getCorrectedText();
        initializeDescription();
        initializeDescriptionCN();
    }


    public String getCorrectedText(){
        String temp = cardStrings.DESCRIPTIONS[0];
        temp = temp.replace("#b", "");
        temp = temp.replace("#y", "");
        return temp;
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/DuplicationPotion.png", m.hb.cX, m.hb.cY, m.hb.cX, m.hb.cY, 2F, 0.6F, false, true), 0.6F));
        //Fake, card is added in the archetype by hand
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DuplicationPotionCard();
    }


}
