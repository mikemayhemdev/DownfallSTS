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
import hermit.cards.Purgatory;
import hermit.characters.hermit;

public class EnPurgatory extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Purgatory";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Purgatory.ID);

    public EnPurgatory() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/purgatory.png", 3, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 13;
        isEthereal = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            isEthereal = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnPurgatory();
    }
}
