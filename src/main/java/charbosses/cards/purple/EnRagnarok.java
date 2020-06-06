package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.stances.EnCalmStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Ragnarok;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnRagnarok extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Ragnarok";
    private static final CardStrings cardStrings;

    public EnRagnarok() {
        super(ID, cardStrings.NAME, "purple/attack/ragnarok", 3, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.RARE, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 5;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;

        this.isMultiDamage = true;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        }


    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new EnRagnarok();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Ragnarok");
    }
}