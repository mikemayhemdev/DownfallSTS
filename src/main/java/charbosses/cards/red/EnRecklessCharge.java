package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import charbosses.cards.status.EnDazed;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class EnRecklessCharge extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Reckless Charge";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Reckless Charge");
    }

    public EnRecklessCharge() {
        super(ID, cardStrings.NAME, "red/attack/reckless_charge", 0, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
        this.cardsToPreview = new Dazed();
        this.tags.add(downfallMod.CHARBOSS_ATTACK);

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new MakeTempCardInDiscardAction(new Dazed(), 1));
        //don't need to create status in boss deck, will be added manually
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnRecklessCharge();
    }
}
