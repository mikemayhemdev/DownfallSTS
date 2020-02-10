package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.EnemyFrost;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.ColdSnap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

public class EnColdSnap extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Cold Snap";
    private static final CardStrings cardStrings;

    public EnColdSnap() {
        super(ID, cardStrings.NAME, "blue/attack/cold_snap", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY);// 19
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new EnemyChannelAction(new EnemyFrost()));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy() {
        return new EnColdSnap();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Cold Snap");
    }
}