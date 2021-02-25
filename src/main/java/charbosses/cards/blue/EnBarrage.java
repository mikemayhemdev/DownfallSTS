package charbosses.cards.blue;

import charbosses.actions.unique.EnemyBarrageAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.EnemyEmptyOrbSlot;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnBarrage extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Barrage";
    private static final CardStrings cardStrings;

    public EnBarrage() {
        super(ID, cardStrings.NAME, "blue/attack/barrage", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 4;
    }

    @Override
    public void applyPowers() {
        int x = 0;
        for (int i = 0; i < AbstractCharBoss.boss.orbs.size(); ++i) {
            if (!(AbstractCharBoss.boss.orbs.get(i) instanceof EnemyEmptyOrbSlot)) {
                x++;
            }
        }
        intentMultiAmt = x;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyBarrageAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL)));
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
