package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyDrawCardNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnPredator extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Predator";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Predator");
    }

    public EnPredator() {
        super(ID, EnPredator.cardStrings.NAME, "green/attack/predator", 2, EnPredator.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.baseDamage = 15;
        this.magicNumber = this.baseMagicNumber = 2;
        this.magicValue = 5;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new ApplyPowerAction(m, m, new EnemyDrawCardNextTurnPower(m, 2), 2));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnPredator();
    }
}
