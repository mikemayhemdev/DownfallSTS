package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnEruption extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Eruption";
    private static final CardStrings cardStrings;

    public EnEruption() {
        super(ID, cardStrings.NAME, "purple/attack/eruption", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.BASIC, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.baseDamage = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        this.addToBot(new EnemyChangeStanceAction("Wrath"));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return super.getPriority(hand) + 10;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }

    }

    public AbstractCard makeCopy() {
        return new EnEruption();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Eruption");
    }
}
