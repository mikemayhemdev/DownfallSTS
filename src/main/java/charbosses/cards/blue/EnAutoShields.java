package charbosses.cards.blue;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.AutoShields;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnAutoShields extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:AutoShields";
    private static final CardStrings cardStrings;

    public EnAutoShields() {
        super("Auto Shields", cardStrings.NAME, "blue/skill/auto_shields", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 11;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.currentBlock == 0) {
            this.addToBot(new GainBlockAction(m, m, this.block));
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 10;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }

    }

    public AbstractCard makeCopy() {
        return new EnAutoShields();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Auto Shields");
    }
}
