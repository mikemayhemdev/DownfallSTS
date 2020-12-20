package charbosses.cards.purple;

import charbosses.actions.unique.EnemyHeadStompAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyEnergyDownPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Fasting;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import com.megacrit.cardcrawl.vfx.combat.FastingEffect;

import java.util.ArrayList;

public class EnFasting extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Fasting2";
    private static final CardStrings cardStrings;

    public EnFasting() {
        super(ID, cardStrings.NAME, "purple/power/fasting", 2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new FastingEffect(m.hb.cX, m.hb.cY, Color.CHARTREUSE)));
        }

        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, m, new DexterityPower(m, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, m, new EnemyEnergyDownPower(m, 1,true), 1));

//        final EnergyManager energy = AbstractCharBoss.boss.energy;
//        --energy.energyMaster;
    }

    public AbstractCard makeCopy() {
        return new EnFasting();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Fasting2");
    }
}
