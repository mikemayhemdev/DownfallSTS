package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnVigilance extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Vigilance";
    private static final CardStrings cardStrings;

    public EnVigilance() {
        super(ID, cardStrings.NAME, "purple/skill/vigilance", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.BASIC, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 8;
        this.block = this.baseBlock;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, this.block));
        this.addToBot(new EnemyChangeStanceAction("Calm"));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return super.getPriority(hand) + 10;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }

    }

    public AbstractCard makeCopy() {
        return new EnVigilance();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Vigilance");
    }
}
