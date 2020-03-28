package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyMantraPower;
import charbosses.stances.EnCalmStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Prostrate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

import java.util.ArrayList;

public class EnProstrate extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Prostrate";
    private static final CardStrings cardStrings;

    public EnProstrate() {
        super(ID, cardStrings.NAME, "purple/skill/prostrate", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
        this.baseBlock = 4;
        this.block = this.baseBlock;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyMantraPower(m, this.magicNumber), this.magicNumber));
        this.addToBot(new GainBlockAction(m, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.ENLIGHTENMENT.NAMES[0].toLowerCase());
    }

    public AbstractCard makeCopy() {
        return new EnProstrate();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Prostrate");
    }
}
