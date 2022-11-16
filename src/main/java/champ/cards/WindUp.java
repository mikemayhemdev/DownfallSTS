package champ.cards;

import champ.ChampMod;
import champ.cards.choiceCards.EnterBerserker;
import champ.cards.choiceCards.EnterDefensive;
import champ.cards.choiceCards.EnterGladiator;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cards.EasyModalChoiceCard;

import java.util.ArrayList;

public class WindUp extends AbstractChampCard {

    public final static String ID = makeID("WindUp");

    //stupid intellij stuff skill, enemy, uncommon

    public WindUp() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        tags.add(ChampMod.OPENER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> modalChoices = new ArrayList<>();
        modalChoices.add(new EnterBerserker());
        modalChoices.add(new EnterDefensive());
        modalChoices.add(new EnterGladiator());
        atb(new SelectCardsCenteredAction(modalChoices, 1, cardStrings.EXTENDED_DESCRIPTION[6], (cards) -> {
            cards.get(0).onChoseThisOption();
        }));
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}