package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.stances.EnCalmStance;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnMeditate extends AbstractBossCard { // TODO: make pretty put card in hand effect
    public static final String ID = "downfall_Charboss:Meditate";
    private static final CardStrings cardStrings;

    public EnMeditate() {
        super(ID, cardStrings.NAME, "purple/skill/meditate", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseBlock = 8;
        this.block = this.baseBlock;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyChangeStanceAction("Calm"));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        if (AbstractCharBoss.boss.stance instanceof EnCalmStance) return 4;
        return super.getPriority(hand) + 10;
    }

    public void upgrade() {
    }

    public AbstractCard makeCopy() {
        return new EnMeditate();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Meditate");
    }
}
