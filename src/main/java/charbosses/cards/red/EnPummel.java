package charbosses.cards.red;

import charbosses.actions.common.EnemyExhaustAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnPummel extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Pummel";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Pummel");
    }

    public EnPummel() {
        super(ID, "Pummel", "red/attack/pummel", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 2;
        this.exhaust = true;
        this.baseMagicNumber = 4;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 1; i < this.magicNumber; ++i) {
            this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));        }


    }


    @Override
    public int getPriority(ArrayList<AbstractCard> hand)
    {
        int badCards = 0;
        for (AbstractCard c : hand){
            if (c.type == CardType.CURSE || c.type == CardType.STATUS){
                badCards++;
            }
        }

        return autoPriority() + (badCards * 5);

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new EnPummel();
    }
}
