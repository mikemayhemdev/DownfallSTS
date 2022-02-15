package charbosses.cards.hermit;

import charbosses.bosses.Hermit.NewAge.ArchetypeAct1SharpshooterNewAge;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hermit.cards.Deadeye;
import hermit.cards.Headshot;
import hermit.characters.hermit;

public class EnDeadeye extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Deadeye";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Deadeye.ID);

    public EnDeadeye() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/deadeye.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, magicNumber), magicNumber));
        if (exhaust && owner.chosenArchetype.actNum == 1) {
            owner.chosenArchetype.defaultToggle = true;
        }
    }

    @Override
    public void onSpecificTrigger() {
        exhaust = true;
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
