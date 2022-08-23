package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedSafety;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.vfx.SmallLaserEffectColored;

import downfall.util.CardIgnore;

@CardIgnore
public class StrikePrimer extends AbstractBronzeCard {

    public final static String ID = makeID("StrikePrimer");

    //stupid intellij stuff attack, enemy, special

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public StrikePrimer() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("MinorBeam.png"));
        isEthereal = true;
        exhaust = true;
        cardsToPreview = new Strike();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        cardsToPreview.upgrade();
    }
}