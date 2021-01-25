package charbosses.cards.curses;

import charbosses.actions.util.CharbossDoCardQueueAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.CBR_Turnip;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

public class EnShame extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Shame";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Shame");
    }

    public EnShame() {
        super(ID, EnShame.cardStrings.NAME, "curse/shame", -2, EnShame.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE, AbstractMonster.Intent.MAGIC);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            if (AbstractCharBoss.boss.hasRelic(CBR_Turnip.ID)) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                        AbstractCharBoss.boss.getRelic(CBR_Turnip.ID).flash();
                        this.addToTop(new TextAboveCreatureAction(m, ApplyPowerAction.TEXT[1]));
                    }
                });
            }
            else {
                this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new FrailPower(AbstractCharBoss.boss, 1, true), 1));
            }
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
        return new EnShame();
    }
}
