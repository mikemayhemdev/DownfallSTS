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

public class MazalethForm extends AbstractAwakenedCard {
    public final static String ID = makeID(MazalethForm.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    boolean chant = false;

    public MazalethForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RegenPower((AbstractCreature)AbstractDungeon.player, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upp() {
        isEthereal = false;
    }
}