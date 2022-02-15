package charbosses.cards.hermit;

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
import hermit.cards.Headshot;
import hermit.cards.Roughhouse;
import hermit.characters.hermit;

public class EnRoughhouse extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Roughhouse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Roughhouse.ID);

    public EnRoughhouse() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/roughhouse.png", 3, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEFEND);
        this.baseDamage = 24;
        this.baseBlock = 16;
        modifyCostForCombat(-3);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (this.owner.hasPower(HermitConcentrationPower.POWER_ID)) {
            addToBot(new GainBlockAction(m, m, block));
        }

    }

    @Override
    public void onSpecificTrigger() {
        destroyIntent();
        intent = AbstractMonster.Intent.ATTACK;
        createIntent();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
            upgradeBlock(4);

        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnRoughhouse();
    }
}
