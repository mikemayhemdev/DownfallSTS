package charbosses.cards.hermit;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.HermitConcentrateAdder;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.downfallMod;
import hermit.cards.Headshot;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnHeadshot extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Headshot";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Headshot.ID);

    public EnHeadshot() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/headshot.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
        tags.add(downfallMod.CHARBOSS_DEADON);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
    }

    @Override
    public void onSpecificTrigger() {
        this.intentActive = false;
        this.createIntent();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        AbstractPower concentration = this.owner.getPower(HermitConcentrationPower.POWER_ID);
        if (concentration != null && concentration.amount > 0) {
            damage *= 2;
        }
        isDamageModified = damage != baseDamage;
        this.intentDmg = damage;
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
        return new EnHeadshot();
    }
}
