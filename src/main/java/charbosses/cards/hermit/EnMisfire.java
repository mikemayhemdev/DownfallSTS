package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.Misfire;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnMisfire extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Misfire";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Misfire.ID);

    public EnMisfire() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/misfire.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 13;

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        atb(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), EnumPatch.HERMIT_GUN2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnMisfire();
    }
}
