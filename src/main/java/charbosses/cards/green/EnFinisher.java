package charbosses.cards.green;

import charbosses.actions.unique.EnemyDamagePerAttackPlayedAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.powers.cardpowers.EnemyInfiniteBladesPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.DamagePerAttackPlayedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public class EnFinisher extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Finisher";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Finisher");
    }

    public EnFinisher() {
        super(ID, EnFinisher.cardStrings.NAME, "green/attack/finisher", 1, EnFinisher.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 6;
        this.isMultiDamage = true;
        this.magicNumber = 0;
        intentMultiAmt = this.magicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyDamagePerAttackPlayedAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int count = this.magicNumber;

        Iterator var2 = AbstractCharBoss.boss.hand.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if (c.type == CardType.ATTACK && !(c instanceof EnFinisher) || c instanceof EnCloakAndDagger)
                ++count;
            if (c instanceof EnBladeDance)
                count += 3;
        }

        this.magicNumber = count;
        this.intentMultiAmt = this.magicNumber;
        this.lockIntentValues = true;


        this.rawDescription = EnFinisher.cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + EnFinisher.cardStrings.EXTENDED_DESCRIPTION[0] + count;

         if (count == 1) {
            this.rawDescription += EnFinisher.cardStrings.EXTENDED_DESCRIPTION[1];

        } else {
            this.rawDescription += EnFinisher.cardStrings.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();
    }


    @Override
    public void onMoveToDiscard() {
        this.rawDescription = EnFinisher.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnFinisher();
    }
}
