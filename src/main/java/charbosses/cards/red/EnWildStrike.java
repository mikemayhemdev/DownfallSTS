package charbosses.cards.red;

import charbosses.actions.common.EnemyMakeTempCardInDrawPileAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.status.EnWound;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class EnWildStrike extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Wild Strike";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Wild Strike");
    }

    public EnWildStrike() {
        super(ID, EnWildStrike.cardStrings.NAME, "red/attack/wild_strike", 1, EnWildStrike.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 12;
        this.tags.add(CardTags.STRIKE);
        this.cardsToPreview = new Wound();
        this.tags.add(downfallMod.CHARBOSS_ATTACK);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new MakeTempCardInDiscardAction(new Wound(), 1));
        //don't need to create in enemy deck, will be added manually
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnWildStrike();
    }
}