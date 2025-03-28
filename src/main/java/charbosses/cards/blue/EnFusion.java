package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.orbs.EnemyLightning;
import charbosses.orbs.EnemyPlasma;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;

public class EnFusion extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Fusion";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Fusion");
    }
    public EnFusion(boolean upgraded) {
        this();
        if (upgraded)
            upgrade();
    }

    public EnFusion() {
        super(ID, cardStrings.NAME, "blue/skill/fusion", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        alwaysDisplayText = true;
    }
    public EnFusion(boolean upgraded,boolean text) {
        super(ID, cardStrings.NAME, "blue/skill/fusion", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        alwaysDisplayText = text;
        if (upgraded)
            upgrade();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new EnemyChannelAction(new EnemyPlasma()));
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 10;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    public AbstractCard makeCopy() {
        return new EnFusion();
    }
}