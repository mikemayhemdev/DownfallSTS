package charbosses.cards.blue;

import charbosses.actions.unique.EnemyBarrageAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.BarrageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Barrage;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.ArrayList;

public class EnBarrage extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Barrage";
    private static final CardStrings cardStrings;

    public EnBarrage() {
        super(ID, cardStrings.NAME, "blue/attack/barrage", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyBarrageAction(p, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * this.owner.orbs.size();
    }

    public AbstractCard makeCopy() {
        return new EnBarrage();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Barrage");
    }
}
