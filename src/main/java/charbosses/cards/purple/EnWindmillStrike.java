package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.WindmillStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnWindmillStrike extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:WindmillStrike";
    private static final CardStrings cardStrings;

    public EnWindmillStrike() {
        super(ID, cardStrings.NAME, "purple/attack/windmill_strike", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.tags.add(CardTags.STRIKE);
    }

    public void onRetained() {
        this.upgradeDamage(this.magicNumber);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
       if (this.cost > 0) return 0;
       return autoPriority();
    }

    public AbstractCard makeCopy() {
        return new EnWindmillStrike();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WindmillStrike");
    }
}
