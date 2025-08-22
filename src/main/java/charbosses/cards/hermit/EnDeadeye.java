package charbosses.cards.hermit;

import basemod.helpers.CardTags;
import charbosses.bosses.AbstractCharBoss;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;
import hermit.cards.Deadeye;
import hermit.characters.hermit;

public class EnDeadeye extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Deadeye";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Deadeye.ID);

    public EnDeadeye() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/deadeye.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        baseDamage = 5;
        baseMagicNumber = magicNumber = 2;
        tags.add(downfallMod.CHARBOSS_DEADON);
        strengthGeneratedIfPlayed = 3;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractPower concentration = this.owner.getPower(HermitConcentrationPower.POWER_ID);
        if (concentration != null && concentration.amount > 0) {
            addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, magicNumber), magicNumber));
        }
    }

    @Override
    public void onSpecificTrigger() {
        intentActive = false;
        this.intent = AbstractMonster.Intent.ATTACK;
        strengthGeneratedIfPlayed = 0;
        createIntent();
        AbstractCharBoss.boss.preApplyIntentCalculations();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDeadeye();
    }
}
