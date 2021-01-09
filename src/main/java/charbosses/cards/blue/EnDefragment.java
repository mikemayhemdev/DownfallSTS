package charbosses.cards.blue;

import charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.cards.blue.Defragment;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.ArrayList;

public class EnDefragment extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Defragment";
    private static final CardStrings cardStrings;

    public EnDefragment() {
        super(ID, cardStrings.NAME, "blue/power/defragment", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        alwaysDisplayText = true;
        focusGeneratedIfPlayed = magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArchetypeAct3OrbsNewAge.resetPretendFocus();
                isDone = true;
            }
        });
        this.addToBot(new ApplyPowerAction(m, m, new FocusPower(m, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            focusGeneratedIfPlayed = magicNumber;
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 50;
    }

    public AbstractCard makeCopy() {
        return new EnDefragment();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defragment");
    }
}
