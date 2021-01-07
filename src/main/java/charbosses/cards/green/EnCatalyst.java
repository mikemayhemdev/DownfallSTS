package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.DoublePoisonAction;
import com.megacrit.cardcrawl.actions.unique.TriplePoisonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Catalyst;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

public class EnCatalyst extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Catalyst";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Catalyst.ID);
    }

    public EnCatalyst() {
        super(ID, EnCatalyst.cardStrings.NAME, "green/skill/catalyst", 1, EnCatalyst.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.STRONG_DEBUFF);
        exhaust = true;
        artifactConsumedIfPlayed = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        if (!this.upgraded) {// 32
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    if (p.hasPower(EnemyPoisonPower.POWER_ID)) {
                        this.addToTop(new ApplyPowerAction(p, m, new EnemyPoisonPower(p, m, p.getPower(EnemyPoisonPower.POWER_ID).amount), p.getPower(EnemyPoisonPower.POWER_ID).amount));
                    }
                    isDone = true;
                }
            });
        } else {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    if (p.hasPower(EnemyPoisonPower.POWER_ID)) {
                        this.addToTop(new ApplyPowerAction(p, m, new EnemyPoisonPower(p, m, p.getPower(EnemyPoisonPower.POWER_ID).amount * 2), p.getPower(EnemyPoisonPower.POWER_ID).amount * 2));
                    }
                    isDone = true;
                }
            });
        }


    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            rawDescription = EnCatalyst.cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCatalyst();
    }
}
