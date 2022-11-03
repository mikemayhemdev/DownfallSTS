package charbossHermit.Hermit;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import hermit.cards.HighCaliber;
import hermit.patches.EnumPatch;

public class EnHighCaliber extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:HighCaliber";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(HighCaliber.ID);

    public EnHighCaliber() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/high_caliber.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, downfallMod.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 12;
        exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnHighCaliber();
    }
}
