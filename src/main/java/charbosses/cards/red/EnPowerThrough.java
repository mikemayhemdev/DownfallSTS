package charbosses.cards.red;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.actions.common.EnemyModifyDamageAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.status.EnWound;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.PowerThrough;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class EnPowerThrough extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:PowerThrough";
    private static final CardStrings cardStrings;

    public EnPowerThrough() {
        super(ID, cardStrings.NAME, "red/skill/power_through", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 15;
        this.cardsToPreview = new Wound();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyMakeTempCardInHandAction(new EnWound(), 2));
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(5);
        }

    }

    public AbstractCard makeCopy() {
        return new EnPowerThrough();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Power Through");
    }
}
