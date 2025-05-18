package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.AddSpellCardAction;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.cards.tokens.spells.DeathCoil;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.updateTimeOffsets;
import static awakenedOne.util.Wiz.atb;

public class AphoticFount extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(AphoticFount.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public AphoticFount() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        cardsToPreview = new AphoticShield();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        spellCards.add(new OrbitingSpells.CardRenderInfo(new AphoticShield()));
        updateTimeOffsets();
        atb(new ConjureAction(false));
    }

    @Override
    public void upp() {
        this.isInnate = true;
    }
}