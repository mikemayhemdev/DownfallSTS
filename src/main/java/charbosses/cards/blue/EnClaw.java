package charbosses.cards.blue;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import java.util.ArrayList;

public class EnClaw extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Gash";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Claw.ID);
    }

    public EnClaw() {
        super(ID, cardStrings.NAME, "blue/attack/claw", 0, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 3;
    }

    public EnClaw(int clawsPlayed){
        this();
        this.baseDamage += (clawsPlayed * 2);
        this.damage = this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {// 40
            this.addToBot(new VFXAction(new ClawEffect(p.hb.cX, p.hb.cY, Color.CYAN, Color.WHITE), 0.1F));// 41
        }

        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));// 44
        CharBossDefect cB = (CharBossDefect) AbstractCharBoss.boss;
        cB.clawsPlayed++;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return super.getPriority(hand) + 5;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }// 51

    public AbstractCard makeCopy() {
        return new EnClaw();
    }
}