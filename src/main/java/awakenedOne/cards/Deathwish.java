package awakenedOne.cards;

import awakenedOne.actions.AddSpellCardAction;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.cards.tokens.spells.DeathCoil;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.updateTimeOffsets;
import static awakenedOne.util.Wiz.atb;

public class Deathwish extends AbstractAwakenedCard {
    public final static String ID = makeID(Deathwish.class.getSimpleName());
    // intellij stuff attack, all_enemy, common, 7, 3, , , ,

    public Deathwish() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new DeathCoil();
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {        AbstractCard card = new AphoticShield();
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            double buf = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            if (buf > 5) {
                buf = buf / 5;
                buf = Math.floor(buf);
                for (int i = 0; i < buf; ++i) {
                    card.upgrade();
                }
            }
        }
        spellCards.add(new OrbitingSpells.CardRenderInfo(card));
        updateTimeOffsets();
        atb(new ConjureAction(false));
    }

    public void upp() {
       isInnate = true;
    }
}