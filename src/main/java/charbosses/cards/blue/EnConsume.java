package charbosses.cards.blue;

import charbosses.actions.orb.EnemyDecreaseMaxOrbAction;
import charbosses.actions.orb.EnemyIncreaseMaxOrbAction;
import charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;

public class EnConsume extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Consume";
    private static final CardStrings cardStrings;

    public EnConsume() {
        super(ID, cardStrings.NAME, "blue/skill/consume", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        focusGeneratedIfPlayed = magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArchetypeAct3OrbsNewAge.resetPretendFocus();
        this.addToBot(new ApplyPowerAction(m, m, new FocusPower(m, this.magicNumber), this.magicNumber));
        this.addToBot(new EnemyDecreaseMaxOrbAction(1));
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
        return new EnConsume();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Consume");
    }
}
