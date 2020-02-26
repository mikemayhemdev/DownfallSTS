package charbosses.cards.red;

import charbosses.actions.unique.EnemyLimitBreakAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class EnLimitBreak extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Limit Break";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Limit Break");
    }

    public EnLimitBreak() {
        super(ID, EnLimitBreak.cardStrings.NAME, "red/skill/limit_break", 1, EnLimitBreak.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.exhaust = true;
        this.limit = 1;
        this.tags.add(EvilWithinMod.CHARBOSS_SETUP);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyLimitBreakAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnLimitBreak();
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        if (AbstractCharBoss.boss != null) {
            int v = 0;
            if (AbstractCharBoss.boss.hasPower(StrengthPower.POWER_ID)) {
                v = AbstractCharBoss.boss.getPower(StrengthPower.POWER_ID).amount;
            }
            for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                if (c.cardID == EnFlex.ID) {
                    v += c.magicNumber;
                }
            }
            return v * 3;
        }
        return 0;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = EnLimitBreak.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
