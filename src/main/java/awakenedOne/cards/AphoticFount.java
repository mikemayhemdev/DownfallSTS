package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

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
        AbstractCard card = new AphoticShield();
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            double buf = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            if (buf > 10) {
                buf = buf / 10;
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

    @Override
    public void upp() {
        this.isInnate = true;
    }
}