package charbosses.cards.hermit;

import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.downfallMod;
import hermit.cards.Headshot;
import hermit.cards.Ricochet;
import hermit.characters.hermit;
import hermit.patches.EndOfTurnPatch;
import hermit.patches.EnumPatch;

public class EnRicochet extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Ricochet";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Ricochet.ID);

    public EnRicochet() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/ricochet.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
        tags.add(downfallMod.CHARBOSS_DEADON);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn),
                        EnumPatch.HERMIT_GUN2));

        AbstractPower concentration = this.owner.getPower(HermitConcentrationPower.POWER_ID);
        if (concentration != null && concentration.amount > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn),
                            EnumPatch.HERMIT_GUN3));
        }


    }

    @Override
    public String overrideIntentText() {

        AbstractPower concentration = this.owner.getPower(HermitConcentrationPower.POWER_ID);
        if (concentration != null && concentration.amount > 0) {
            return intentDmg + "x2";
        }
        return super.overrideIntentText();
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
        return new EnRicochet();
    }
}
