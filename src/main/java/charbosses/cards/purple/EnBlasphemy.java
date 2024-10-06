package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.stances.EnDivinityStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;

public class EnBlasphemy extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Blasphemy";
    private static final CardStrings cardStrings;

    public EnBlasphemy() {
        super("Blasphemy", cardStrings.NAME, "purple/skill/blasphemy", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.exhaust = true;
        this.energyGeneratedIfPlayed = 3;
        damageMultGeneratedIfPlayed = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new EnemyChangeStanceAction(EnDivinityStance.STANCE_ID));
        this.addToBot(new ApplyPowerAction(m, m, new EndTurnDeathPower(m))); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }


    }

    public AbstractCard makeCopy() {
        return new EnBlasphemy();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blasphemy");
    }
}