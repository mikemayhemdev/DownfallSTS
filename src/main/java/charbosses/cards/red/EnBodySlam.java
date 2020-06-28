package charbosses.cards.red;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnBodySlam extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Body Slam";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Body Slam");
    }

    public EnBodySlam() {
        super(ID, EnBodySlam.cardStrings.NAME, "red/attack/body_slam", 1, EnBodySlam.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 0;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 0;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.baseDamage = m.currentBlock;
        this.calculateCardDamage(m);
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.rawDescription = EnBodySlam.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = AbstractCharBoss.boss.currentBlock;
        super.applyPowers();
        this.rawDescription = EnBodySlam.cardStrings.DESCRIPTION;
        this.rawDescription += EnBodySlam.cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = EnBodySlam.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = EnBodySlam.cardStrings.DESCRIPTION;
        this.rawDescription += EnBodySlam.cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBodySlam();
    }
}
