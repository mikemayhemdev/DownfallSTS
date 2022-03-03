package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.ItchyTrigger;
import hermit.cards.Spite;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnSpite extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Spite";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Spite.ID);

    public EnSpite() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/spite.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseBlock = 13;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSpite();
    }
}
