package charbosses.cards.colorless;

import charbosses.actions.unique.EnemyApotheosisAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import java.util.ArrayList;

public class EnApotheosis extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Apotheosis";
    private static final CardStrings cardStrings;

    public EnApotheosis() {
        super(ID, cardStrings.NAME, "colorless/skill/apotheosis", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyApotheosisAction());
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    public AbstractCard makeCopy() {
        return new EnApotheosis();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Apotheosis");
    }
}
