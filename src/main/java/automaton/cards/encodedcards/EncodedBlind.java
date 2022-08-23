//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package automaton.cards.encodedcards;

import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import java.util.Iterator;

import downfall.util.CardIgnore;

@CardIgnore
public class EncodedBlind extends AbstractBronzeCard {
    public static final String ID = "bronze:EncodedBlind";
    private static final CardStrings cardStrings;

    public EncodedBlind() {
        super("bronze:EncodedBlind", cardStrings.NAME, "colorless/skill/blind", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upp() {
        upgrade();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
        } else {
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while(var3.hasNext()) {
                AbstractMonster mo = (AbstractMonster)var3.next();
                this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AttackEffect.NONE));
            }
        }

    }

    public void upgrade() {
        this.target = CardTarget.ALL_ENEMY;
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();

    }

    public AbstractCard makeCopy() {
        return new EncodedBlind();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("bronze:EncodedBlind");
    }
}
