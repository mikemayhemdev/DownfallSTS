package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.SandsOfTime;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnSandsOfTime extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:SandsOfTime";
    private static final CardStrings cardStrings;

    public EnSandsOfTime() {
        super(ID, cardStrings.NAME, "purple/attack/sands_of_time", 4, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 20;
        this.selfRetain = true;
    }

    public EnSandsOfTime(int costReduce){
        this();
        modifyCostForCombat(-costReduce);
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 0;
    }

    public void onRetained() {
        this.addToBot(new ReduceCostAction(this));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
        }

    }

    public AbstractCard makeCopy() {
        return new EnSandsOfTime();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SandsOfTime");
    }
}
