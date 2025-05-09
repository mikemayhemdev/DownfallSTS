package awakenedOne.cards.tokens.spells;

import awakenedOne.relics.EyeOfTheOccult;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;
import theHexaghost.relics.CandleOfCauterizing;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class ESPSpell extends AbstractSpellCard {
    public final static String ID = makeID(ESPSpell.class.getSimpleName());
    // intellij stuff skill, all_enemy, , , , , 7, 2

    public ESPSpell() {
        super(ID, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}