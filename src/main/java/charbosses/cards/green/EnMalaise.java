package charbosses.cards.green;

import charbosses.actions.unique.EnemyBaneAction;
import charbosses.actions.unique.EnemyMalaiseAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.MalaiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Bane;
import com.megacrit.cardcrawl.cards.green.Malaise;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnMalaise extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Bane";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Malaise.ID);
    }

    public EnMalaise() {
        super(ID, EnMalaise.cardStrings.NAME, "green/skill/malaise", -1, EnMalaise.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.STRONG_DEBUFF);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyMalaiseAction((AbstractCharBoss)m, this.upgraded, this.freeToPlayOnce, this.energyOnUse));// 31
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            rawDescription = EnMalaise.cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnMalaise();
    }
}
