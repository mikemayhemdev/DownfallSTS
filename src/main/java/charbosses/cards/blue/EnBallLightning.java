package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.orbs.EnemyLightning;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnBallLightning extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Ball Lightning";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Ball Lightning");
    }

    public EnBallLightning() {
        super(ID, cardStrings.NAME, "blue/attack/ball_lightning", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);// 19
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 7;
    }

    @Override
    public String overrideIntentText() {
        return intentDmg + "+ ("  + ( 3 + AbstractEnemyOrb.masterPretendFocus + EnZap.getFocusAmountSafe()) + ")";
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        for (int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new EnemyChannelAction(new EnemyLightning()));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }

    }// 51

    public AbstractCard makeCopy() {
        return new EnBallLightning();
    }
}