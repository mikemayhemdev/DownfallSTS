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
import downfall.downfallMod;

public class EnHeadbutt extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Headbutt";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Headbutt");
    }

    public EnHeadbutt() {
        super(ID, EnHeadbutt.cardStrings.NAME, "red/attack/headbutt", 1, EnHeadbutt.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 9;
        this.tags.add(downfallMod.CHARBOSS_ATTACK);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        //this.addToBot(new EnemyDiscardPileToTopOfDeckAction((AbstractCharBoss) m));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnHeadbutt();
    }
}
