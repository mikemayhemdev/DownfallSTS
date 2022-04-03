package timeeater.cards;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.DarkShackles;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Arrest extends AbstractTimeEaterCard {
    public final static String ID = makeID("Arrest");
    // intellij stuff skill, all, uncommon, , , , , , 

    public Arrest() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        cardsToPreview = new DarkShackles();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, -1));
        forAllMonstersLiving(q -> applyToEnemy(q, new StrengthPower(q, -1)));
        AbstractCard q = new DarkShackles();
        if (upgraded) CardModifierManager.addModifier(q, new RetainMod());
        atb(new SuspendAction(q));
    }

    public void upp() {
        AbstractCard q = new DarkShackles();
        CardModifierManager.addModifier(q, new RetainMod());
        cardsToPreview = q;
        uDesc();
    }
}