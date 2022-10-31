package charbosses.cards.blue;

import charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyReboundPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Reprogram;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class EnReprogram extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Reprogram";
    private static final CardStrings cardStrings;

    public EnReprogram() {
        super("Reprogram", cardStrings.NAME, "blue/skill/reprogram", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArchetypeAct3OrbsNewAge.resetPretendFocus();
                isDone = true;
            }
        });
        this.addToBot(new ApplyPowerAction(m, m, new FocusPower(p, -this.magicNumber), -this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(p, this.magicNumber), 1));
        this.addToBot(new ApplyPowerAction(m, m, new DexterityPower(p, this.magicNumber), 1));
    }

    public AbstractCard makeCopy() {
        return new EnReprogram();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Reprogram");
    }
}