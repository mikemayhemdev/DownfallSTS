package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.cardmods.PlayMeTwiceCardmod;
import automaton.cards.encodedcards.EncodedCleave;
import automaton.relics.ElectromagneticCoil;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Terminator extends AbstractBronzeCard {

    public final static String ID = makeID("Terminator");

    //stupid intellij stuff skill, self, common


    public Terminator() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        cardsToPreview = new TerminatorRepeatCard();
        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addCardToFunction(cardsToPreview);
    }


    public void upp() {
       exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!((AbstractDungeon.player.hasRelic(ElectromagneticCoil.ID)) && FunctionHelper.held.group.size() == 2) || (AbstractDungeon.player.hasRelic(ElectromagneticCoil.ID)) && FunctionHelper.held.group.size() == 3) {

            return true;
        }
        else return false;
    }
}