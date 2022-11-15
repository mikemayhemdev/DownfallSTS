package champ.cards;

import champ.ChampMod;
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
        modalChoices.add(new EasyModalChoiceCard("champ:EnterDefensive", cardStrings.EXTENDED_DESCRIPTION[0], cardStrings.EXTENDED_DESCRIPTION[1], () -> atb(new ChangeStanceAction(DefensiveStance.STANCE_ID))));
        modalChoices.add(new EasyModalChoiceCard("champ:EnterBerserker", cardStrings.EXTENDED_DESCRIPTION[2], cardStrings.EXTENDED_DESCRIPTION[3], () -> atb(new ChangeStanceAction(BerserkerStance.STANCE_ID))));
        modalChoices.add(new EasyModalChoiceCard("champ:EnterGladiator", cardStrings.EXTENDED_DESCRIPTION[4], cardStrings.EXTENDED_DESCRIPTION[5], () -> atb(new ChangeStanceAction(GladiatorStance.STANCE_ID))));
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