package charbosses.cards.green;

import charbosses.actions.unique.EnemyDamagePerAttackPlayedAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnFinisher extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Finisher";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Finisher");
    }

    public EnFinisher() {
        this(0);
    }

    public EnFinisher(int hitCount) {
        super(ID, EnFinisher.cardStrings.NAME, "green/attack/finisher", 1, EnFinisher.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 6;
        this.isMultiDamage = true;
        this.magicNumber = hitCount;
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

//        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
//            if (c.type == CardType.ATTACK && !(c instanceof EnFinisher) || c instanceof EnCloakAndDagger)
//                ++count;
//            if (c instanceof EnBladeDance)
//                count += 3;
//        }

//        this.magicNumber = count;
//        this.intentMultiAmt = this.magicNumber;
//        this.lockIntentValues = false;
//        this.calculateCardDamage(null);
//        this.lockIntentValues = true;


        this.rawDescription = EnFinisher.cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + EnFinisher.cardStrings.EXTENDED_DESCRIPTION[0] + count;

        if (count == 1) {
            this.rawDescription += EnFinisher.cardStrings.EXTENDED_DESCRIPTION[1];

        } else {
            this.rawDescription += EnFinisher.cardStrings.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();
    }

    public void increaseHits(int amount) {
        this.magicNumber += amount;
        this.intentMultiAmt = this.magicNumber;
        this.lockIntentValues = false;
        this.createIntent();
//        this.lockIntentValues = true;
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
