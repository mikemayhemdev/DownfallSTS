package charbosses.cards.status;

import charbosses.actions.util.CharbossDoCardQueueAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnBurn extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Burn";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Burn");
    }

    public EnBurn() {
        super(ID, EnBurn.cardStrings.NAME, "status/burn", -2, EnBurn.cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.NONE, AbstractMonster.Intent.MAGIC);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.addToTop(new DamageAction(p, new DamageInfo(p, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.addToBottom(new CharbossDoCardQueueAction(this));
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBurn();
    }
}
