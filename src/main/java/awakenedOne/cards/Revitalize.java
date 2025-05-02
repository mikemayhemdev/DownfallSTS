package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;

public class Revitalize extends AbstractAwakenedCard {
    public final static String ID = makeID(Revitalize.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    boolean chant = false;

    public Revitalize() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagic = 4;
        this.secondMagic = this.baseSecondMagic;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.chant)
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RegenPower((AbstractCreature)AbstractDungeon.player, this.magicNumber), this.magicNumber));
        if (this.chant)
            chant();
    }

    @Override
    public void chant() {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RegenPower((AbstractCreature)AbstractDungeon.player, this.secondMagic), this.secondMagic));
        checkOnChant();
    }

    public void triggerWhenDrawn() {
        this.chant = false;
    }

    public void triggerOnCardPlayed(AbstractCard card) {
        if (card.type == CardType.POWER && AbstractDungeon.player.hand.contains((AbstractCard)this))
            this.chant = true;
    }

    @Override
    public void onMoveToDiscard() {
        this.chant = false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}