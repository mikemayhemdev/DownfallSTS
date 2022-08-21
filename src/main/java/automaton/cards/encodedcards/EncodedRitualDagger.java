//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package automaton.cards.encodedcards;

import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.RitualDaggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EncodedRitualDagger extends AbstractBronzeCard {
    public static final String ID = "bronze:EncodedRitualDagger";
    private static final CardStrings cardStrings;

    public EncodedRitualDagger() {
        super("bronze:EncodedRitualDagger", cardStrings.NAME, "colorless/attack/ritual_dagger", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.misc = 15;
        this.baseDamage = this.misc;
        this.exhaust = true;
    }

    @Override
    public void upp() {
        upgrade();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }

    public void applyPowers() {
        this.baseBlock = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeMagicNumber(2);
            this.upgradeName();
        }

    }

    public AbstractCard makeCopy() {
        return new EncodedRitualDagger();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("bronze:EncodedRitualDagger");
    }
}
