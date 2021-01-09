package charbosses.cards.colorless;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Blind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;
import java.util.Iterator;

public class EnBlind extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Blind";
    private static final CardStrings cardStrings;

    public EnBlind() {
        super(ID, cardStrings.NAME, "colorless/skill/blind", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.DEBUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        artifactConsumedIfPlayed = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, this.magicNumber + 1, true), this.magicNumber + 1));


    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 10;
    }

    public AbstractCard makeCopy() {
        return new EnBlind();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blind");
    }
}
