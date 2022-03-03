package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.Misfire;
import hermit.cards.SprayPray;
import hermit.characters.hermit;

public class EnSprayNPray extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:SprayNPray";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(SprayPray.ID);

    public EnSprayNPray() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/showdown.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 5;
        isMultiDamage = true;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (int i = 0; i < 3; i++) {
            addToBot(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }
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
        return new EnSprayNPray();
    }
}
