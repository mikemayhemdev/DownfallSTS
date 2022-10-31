package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.actions.unique.EnemyDarkImpulseAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.orbs.EnemyDark;
import charbosses.orbs.EnemyFrost;
import charbosses.orbs.EnemyLightning;
import charbosses.vfx.EnemyRainbowCardEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;

import java.util.ArrayList;

public class EnRainbow extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Rainbow";
    private static final CardStrings cardStrings;

    public EnRainbow() {
        super(ID, cardStrings.NAME, "blue/skill/rainbow", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 3;
        this.exhaust = true;
        alwaysDisplayText = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new EnemyRainbowCardEffect()));
        this.addToBot(new EnemyChannelAction(new EnemyLightning()));
        this.addToBot(new EnemyChannelAction(new EnemyFrost()));
        this.addToBot(new EnemyChannelAction(new EnemyDark()));
    }

    public static int getFocusAmountSafe() {
        if (AbstractCharBoss.boss.hasPower(FocusPower.POWER_ID)) {
            return AbstractCharBoss.boss.getPower(FocusPower.POWER_ID).amount;
        }
        return 0;
    }

    @Override
    public String overrideIntentText() {
        return "(" + ( 3 + AbstractEnemyOrb.masterPretendFocus + getFocusAmountSafe()) + ")";
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 10;
    }

    public AbstractCard makeCopy() {
        return new EnRainbow();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Rainbow");
    }
}
