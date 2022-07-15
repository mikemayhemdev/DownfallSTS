package charbosses.cards.blue;

import charbosses.actions.unique.EnemyBarrageAction;
import charbosses.actions.unique.EnemyThunderStrikeAction;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.EnemyFrost;
import charbosses.orbs.EnemyLightning;
import com.megacrit.cardcrawl.actions.defect.NewThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;

import java.util.ArrayList;
import java.util.Iterator;

public class EnThunderStrike extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Thunder Strike";
    private static final CardStrings cardStrings;

    public EnThunderStrike() {
        this(1);
    }

    public EnThunderStrike(int hitCount) {
        super(ID, cardStrings.NAME, "blue/attack/thunder_strike", 3, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
        this.isMultiDamage = true;
        this.baseMagicNumber = hitCount;
        this.magicNumber = baseMagicNumber;
        intentMultiAmt = this.magicNumber;
        this.tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseMagicNumber = getLightningCount();
        this.magicNumber = this.baseMagicNumber;

        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new EnemyThunderStrikeAction(this, p));
        }
    }

    public static int getLightningCount() {
        int lightningCount = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof EnemyLightning) {
                ++lightningCount;
            }
        }
        return lightningCount;
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = getLightningCount();
        this.magicNumber = baseMagicNumber;

        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }

        this.initializeDescription();
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
        return new EnThunderStrike();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Thunder Strike");
    }
}
