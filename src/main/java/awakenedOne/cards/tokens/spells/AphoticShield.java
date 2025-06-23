package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeID;

public class AphoticShield extends AbstractSpellCard {
    public final static String ID = makeID(AphoticShield.class.getSimpleName());
    // intellij stuff skill, self, , , , , 2, 1

    public AphoticShield() {
        super(ID, 0, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        loadJokeCardImage(this, ID+".png");}

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, magicNumber), magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}