package charbosses.cards.red;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.EnemyCardGroup;
import charbosses.powers.cardpowers.EnemyDoubleTapPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class EnDoubleTap extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Double Tap";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Double Tap");
    }

    public EnDoubleTap() {
        super(ID, EnDoubleTap.cardStrings.NAME, "red/skill/double_tap", 1, EnDoubleTap.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 20;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyDoubleTapPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = EnDoubleTap.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.magicValue -= 5;
        }
    }

    @Override
    public int getPriority() {
        if (!recursionCheck && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractCharBoss.boss != null && !AbstractCharBoss.boss.hand.isEmpty()) {
            recursionCheck = true;
            AbstractBossCard c = ((EnemyCardGroup) (AbstractCharBoss.boss.hand)).getHighestValueCard(CardType.ATTACK);
            if (c != null) {
                int p = c.getPriority();
                recursionCheck = false;
                return p + 1;
            }
            recursionCheck = false;
        }
        return 3;
    }

    @Override
    public int getValue() {
        if (!recursionCheck && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractCharBoss.boss != null && !AbstractCharBoss.boss.hand.isEmpty()) {
            recursionCheck = true;
            AbstractBossCard c = ((EnemyCardGroup) (AbstractCharBoss.boss.hand)).getHighestValueCard(CardType.ATTACK);
            if (c != null) {
                int v = c.getValue();
                recursionCheck = false;
                return v * Math.max(1, c.costForTurn);
            }
            recursionCheck = false;
        }
        return super.getValue();
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDoubleTap();
    }
}
